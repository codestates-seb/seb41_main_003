package com.mainproject.server.auth.oauth2;

import com.google.gson.Gson;
import com.mainproject.server.auth.redis.service.RefreshService;
import com.mainproject.server.auth.token.JwtAuthorityUtils;
import com.mainproject.server.auth.token.JwtTokenizer;
import com.mainproject.server.auth.token.Token;
import com.mainproject.server.constant.ErrorCode;
import com.mainproject.server.constant.LoginType;
import com.mainproject.server.constant.UserStatus;
import com.mainproject.server.dto.AuthSuccessTokenResponseDto;
import com.mainproject.server.dto.ResponseDto;
import com.mainproject.server.exception.ServiceLogicException;
import com.mainproject.server.user.entity.User;
import com.mainproject.server.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
@Slf4j
@Component
public class OAuth2UserSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final UserService userService;

    private final JwtTokenizer jwtTokenizer;

    private final RefreshService refreshService;

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException {
        log.info("# OAuth2 Authenticated successfully!");
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String email = String.valueOf(oAuth2User.getAttributes().get("email"));
        String nickName = String.valueOf(oAuth2User.getAttributes().get("name"));
        User user = saveUser(email, nickName);

        String redirect = getRedirectUri(response, user);
        getRedirectStrategy().sendRedirect(request, response, redirect);
    }

    private String getRedirectUri(HttpServletResponse response, User user) {
        log.info("# OAuth2 Get RedirectUri!");
        Token token = jwtTokenizer.delegateToken(user);
        String accessToken = token.getAccessToken();
        String refreshToken = token.getRefreshToken();
        refreshService.createRefresh(user.getEmail(), refreshToken);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.OK.value());
        return UriComponentsBuilder.fromUriString("http://localhost:3000/auth?")
                .queryParam("Authorization", accessToken)
                .queryParam("userId", user.getUserId().toString())
                .queryParam("userStatus", user.getUserStatus().name())
                .build()
                .toUriString();
    }

    private User saveUser(String email, String nickName) {
        try {
            log.info(" #### OAuth2 Login ");
            User user = userService.verifiedUserByEmail(email);
            if (user.getLoginType().equals(LoginType.SOCIAL)) {
                return user;
            } else {
                throw new ServiceLogicException(ErrorCode.USER_EMAIL_EXISTS);
            }
        } catch (ServiceLogicException se) {
            if (se.getErrorCode().equals(ErrorCode.USER_NOT_FOUND)) {
                User build = User.builder()
                        .email(email)
                        .nickName(nickName)
                        .loginType(LoginType.SOCIAL)
                        .userStatus(UserStatus.NONE)
                        .roles(JwtAuthorityUtils.USER_ROLES_STRING_CALL)
                        .build();
                return userService.createUser(build);
            } else {
                throw se;
            }
        }
    }
}
