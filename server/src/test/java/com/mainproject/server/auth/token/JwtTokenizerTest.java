package com.mainproject.server.auth.token;

import com.mainproject.server.auth.redis.dto.RefreshDto;
import com.mainproject.server.auth.redis.service.RefreshService;
import com.mainproject.server.constant.ErrorCode;
import com.mainproject.server.exception.ServiceLogicException;
import com.mainproject.server.user.entity.User;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Key;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class JwtTokenizerTest {

    private final String secretKey = "127409719fadsfsadf057902cvdfgd92337501734091934";

    @Mock
    private JwtAuthorityUtils jwtAuthorityUtils;

    @Mock
    private RefreshService refreshService;

    @InjectMocks
    private JwtTokenizer jwtTokenizer;

    @BeforeEach
    public void beforeEach() {
        jwtTokenizer.setSecretKey(secretKey);
    }

    @Test
    @DisplayName("JWT Token 발급 TEST")
    void givenUserWhenDelegateTokenThenReturnToken() {
        // Given
        User user = User.builder()
                .email("test@test.com")
                .roles(JwtAuthorityUtils.USER_ROLES_STRING_CALL)
                .build();
        // When
        Token token = jwtTokenizer.delegateToken(user);
        String email = jwtTokenizer.getEmail(token.getAccessToken());
        // Then
        assertThat(email).isEqualTo(user.getEmail());
    }

    @Test
    @DisplayName("AccessToken 검증 로직 TEST - 만료된 토큰 검증")
    void givenExpiredAccessTokenWhenVerifyAccessTokenThenThrowServiceLogicException() {
        // Given
        Token token = getToken(0);
        // When
        Throwable throwable = catchThrowable(
                () -> jwtTokenizer.verifyAccessToken(token.getAccessToken())
        );
        // Then
        assertThat(throwable)
                .isInstanceOf(ServiceLogicException.class)
                .hasMessageContaining(ErrorCode.EXPIRED_ACCESS_TOKEN.getMessage());
    }

    @Test
    @DisplayName("Refresh Token 재발급 로직 TEST")
    void givenRefreshTokenWhenVerifyRefreshTokenThenReturnResponseHeader() throws IOException {
        // Given
        String email = "test@test.com";
        Token token = getToken(1);
        RefreshDto refreshDto = new RefreshDto(
                email,token.getRefreshToken(), LocalDateTime.now()
        );
        HttpServletResponse response = new MockHttpServletResponse();
        given(refreshService.getRefresh(anyString())).willReturn(refreshDto);
        // When
        jwtTokenizer.verifyRefreshToken(email, response);
        String authorization = response.getHeader("Authorization")
                .replace("Bearer ", "");
        // Then
        assertThat(jwtTokenizer.getEmail(authorization)).isEqualTo(email);
    }

    @Test
    @DisplayName("Refresh Token 재발급 로직 TEST - Refresh Token 만료")
    void givenExpiredRefreshTokenWhenVerifyRefreshTokenThenThrowServiceLogicException() throws IOException {
        // Given
        String email = "test@test.com";
        Token token = getToken(0);
        RefreshDto refreshDto = new RefreshDto(
                email,token.getRefreshToken(), LocalDateTime.now()
        );
        HttpServletResponse response = new MockHttpServletResponse();
        given(refreshService.getRefresh(anyString())).willReturn(refreshDto);
        // When
        Throwable throwable = catchThrowable(
                () -> jwtTokenizer.verifyRefreshToken(email, response)
        );
        // Then
        assertThat(throwable)
                .isInstanceOf(ServiceLogicException.class)
                .hasMessageContaining(ErrorCode.EXPIRED_REFRESH_TOKEN.getMessage());
    }

    private Token getToken(int expirationMinutes) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("username", "test@test.com");
        claims.put("roles", "USER");
        return generateToken(
                claims,
                (String) claims.get("username"),
                jwtTokenizer.encodeBase64SecretKey(secretKey),
                expirationMinutes
        );
    }
    private Token generateToken(
            Map<String, Object> claims,
            String subject,
            String base64EncodedSecretKey,
            int expirationMinutes
    ) {
        Key key = jwtTokenizer.getKeyFromBase64EncodedSecretKey(base64EncodedSecretKey);

        return new Token(
                Jwts.builder()
                        .setClaims(claims)
                        .setSubject(subject)
                        .setIssuedAt(Calendar.getInstance().getTime())
                        .setExpiration(jwtTokenizer.getTokenExpiration(expirationMinutes))
                        .signWith(key)
                        .compact(),
                Jwts.builder()
                        .setSubject(subject)
                        .setIssuedAt(Calendar.getInstance().getTime())
                        .setExpiration(jwtTokenizer.getTokenExpiration(expirationMinutes))
                        .signWith(key)
                        .compact());

    }

}