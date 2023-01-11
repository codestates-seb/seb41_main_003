package com.mainproject.server.auth.oauth2.service;

import com.mainproject.server.auth.oauth2.attribute.OAuth2Attribute;
import com.mainproject.server.auth.token.JwtAuthorityUtils;
import com.mainproject.server.constant.ErrorCode;
import com.mainproject.server.constant.LoginType;
import com.mainproject.server.constant.UserStatus;
import com.mainproject.server.exception.ServiceLogicException;
import com.mainproject.server.user.entity.User;
import com.mainproject.server.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserService userService;



    @Override
    public OAuth2User loadUser(
            OAuth2UserRequest userRequest
    ) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> oAuth2UserService = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = oAuth2UserService.loadUser(userRequest);
        String email = String.valueOf(oAuth2User.getAttributes().get("email"));
        String nickName = String.valueOf(oAuth2User.getAttributes().get("name"));
        User saveUser = saveUser(email, nickName);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String userNameAttributeName = userRequest.getClientRegistration()
                .getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

        OAuth2Attribute oAuth2Attribute =
                OAuth2Attribute.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());

        var memberAttribute = oAuth2Attribute.convertToMap();
        return new DefaultOAuth2User(
                JwtAuthorityUtils.getAuthorities(saveUser.getRoles()),
                memberAttribute,
                userNameAttributeName
        );

    }

    private User saveUser(String email, String nickName) {
        try {
            log.info(" #### OAuth2 Login ");
            return userService.verifiedUserByEmail(email);
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
