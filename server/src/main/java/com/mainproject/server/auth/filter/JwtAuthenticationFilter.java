package com.mainproject.server.auth.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mainproject.server.auth.dto.LoginDto;
import com.mainproject.server.auth.redis.service.RefreshService;
import com.mainproject.server.auth.token.JwtTokenizer;
import com.mainproject.server.auth.token.Token;
import com.mainproject.server.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;

    private final JwtTokenizer jwtTokenizer;

    private final RefreshService refreshService;


    /* Login Dto로 전송받은 데이터를
    * UsernamePasswordAuthenticationToken 으로 변환 하여 Security Flow 시작 */
    @SneakyThrows
    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws AuthenticationException {
        ObjectMapper objectMapper = new ObjectMapper();
        LoginDto loginDto = objectMapper.readValue(request.getInputStream(), LoginDto.class);
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());

        return authenticationManager.authenticate(usernamePasswordAuthenticationToken);
    }

    /* 인증에 성공시 응답 헤더 설정 */
    @Override
    protected void successfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain,
            Authentication authResult
    ) throws IOException, ServletException {
        User user = (User) authResult.getPrincipal();
        Long userId = user.getUserId();


        Token token = jwtTokenizer.delegateToken(user);
        String accessToken = token.getAccessToken();
        String refreshToken = token.getRefreshToken();
        refreshService.createRefresh(user.getEmail(), refreshToken);
        response.setHeader("Authorization", "Bearer " + accessToken);
        response.setHeader("userId", userId.toString());
        response.setHeader("userStatus", user.getUserStatus().name());

        this.getSuccessHandler().onAuthenticationSuccess(request, response, authResult);
    }
}
