package com.mainproject.server.auth.token;

import com.mainproject.server.auth.redis.dto.RefreshDto;
import com.mainproject.server.auth.redis.service.RefreshService;
import com.mainproject.server.constant.ErrorCode;
import com.mainproject.server.exception.ServiceLogicException;
import com.mainproject.server.user.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class JwtTokenizer {

    @Getter
    @Value("${JWT_SECRET_KEY}")
    private String secretKey;

    @Getter
    private final int accessTokenExpirationMinutes = 10;

    @Getter
    private final int refreshTokenExpirationMinutes = 30;

    private final JwtAuthorityUtils jwtAuthorityUtils;

    private final RefreshService refreshService;

    public String encodeBase64SecretKey(String secretKey) {
        return Encoders.BASE64.encode(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    /* jwt 토큰을 생성 */
    private Token generateToken(
            Map<String, Object> claims,
            String subject,
            String base64EncodedSecretKey
    ) {
        Key key = getKeyFromBase64EncodedSecretKey(base64EncodedSecretKey);

        return new Token(
                Jwts.builder()
                        .setClaims(claims)
                        .setSubject(subject)
                        .setIssuedAt(Calendar.getInstance().getTime())
                        .setExpiration(getTokenExpiration(accessTokenExpirationMinutes))
                        .signWith(key)
                        .compact(),
                Jwts.builder()
                        .setSubject(subject)
                        .setIssuedAt(Calendar.getInstance().getTime())
                        .setExpiration(getTokenExpiration(refreshTokenExpirationMinutes))
                        .signWith(key)
                        .compact());

    }

    /* user 매개변수를 받아 jwt 토큰을 생성 */
    public Token delegateToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("username", user.getEmail());
        claims.put("roles", user.getRoles());

        String subject = user.getEmail();

        String base64SecretKey = encodeBase64SecretKey(getSecretKey());

        return generateToken(claims, subject, base64SecretKey);
    }

    /* RefreshToken 을 검증하여 토큰을 재 발급 */
    private void reIssueToken(
            String refreshToken,
            String base64SecretKey,
            HttpServletResponse response,
            String email
    ) {
        Map<String, Object> claims = new HashMap<>();
        String subject = getEmail(refreshToken);
        claims.put("username", subject);
        claims.put("roles", jwtAuthorityUtils.createRoles(subject));
        // Todo 이메일을 받을 필요 없을것 같다. Refactoring
        Token token = generateToken(claims, subject, base64SecretKey);
        String newAccessToken = token.getAccessToken();
        String newRefreshToken = token.getRefreshToken();
        refreshService.createRefresh(email, newRefreshToken);
        response.setHeader("Authorization", "Bearer " + newAccessToken);
    }

    /* Refresh Token 검증 */
    public void verifyRefreshToken(
            String email,
            HttpServletResponse response
    ) throws IOException {


        String base64SecretKey = encodeBase64SecretKey(getSecretKey());
        try {
            RefreshDto refreshToken = refreshService.getRefresh(email);
            if (refreshToken == null) {
                throw new ServiceLogicException(ErrorCode.TOKEN_NOT_NULL);
            }
            verifySignature(refreshToken.getRefreshToken(), base64SecretKey);
            reIssueToken(refreshToken.getRefreshToken(), base64SecretKey, response, email);
        } catch (ExpiredJwtException | ServiceLogicException ee) {
            throw new ServiceLogicException(ErrorCode.EXPIRED_REFRESH_TOKEN);
        } catch (Exception e) {
            throw e;
        }
    }

    /* Refresh Token 삭제 */
    public void deleteRefresh(String accessToken) {
        refreshService.deleteRefresh(getEmail(accessToken));
    }

    /* AccessToken 검증 */
    public void verifyAccessToken(
            String accessToken
    ) throws IOException {
        String base64SecretKey = encodeBase64SecretKey(getSecretKey());
        try {
            verifySignature(accessToken, base64SecretKey);
        } catch (ExpiredJwtException ee) {
            throw new ServiceLogicException(ErrorCode.EXPIRED_ACCESS_TOKEN);
        } catch (Exception e) {
            throw e;
        }
    }

    /* Secret key 생성 */
    public Key getKeyFromBase64EncodedSecretKey(String base64EncodedSecretKey) {
        byte[] keyBytes = Decoders.BASE64.decode(base64EncodedSecretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /* Server에서 발급한 토큰이 맞는지 검증 */
    public void verifySignature(String jws, String base64EncodedSecretKey) {
        Key key = getKeyFromBase64EncodedSecretKey(base64EncodedSecretKey);

        Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(jws);
    }

    /* Claims 정보를 가져옴 */
    public Jws<Claims> getClaims(String jws, String base64EncodedSecretKey) {
        Key key = getKeyFromBase64EncodedSecretKey(base64EncodedSecretKey);
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(jws);
    }

    /* Token의 만료 기한 설정 */
    public Date getTokenExpiration(int expirationMinutes) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, expirationMinutes);
        return calendar.getTime();
    }

    public String getEmail(String token) {
        Key key = getKeyFromBase64EncodedSecretKey(encodeBase64SecretKey(secretKey));
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

}
