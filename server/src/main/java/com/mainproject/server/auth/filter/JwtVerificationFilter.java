package com.mainproject.server.auth.filter;

import com.mainproject.server.auth.token.JwtAuthorityUtils;
import com.mainproject.server.auth.token.JwtTokenizer;
import com.mainproject.server.constant.ErrorCode;
import com.mainproject.server.exception.ServiceLogicException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;


@RequiredArgsConstructor
public class JwtVerificationFilter extends OncePerRequestFilter {

    private final JwtTokenizer jwtTokenizer;

    private final JwtAuthorityUtils authorityUtils;

    /* 인증 후 권한 검증 Filter
    * GrantedAuthority 를 생성하여
    * UsernamePasswordAuthenticationToken 적용 후
    * SecurityContextHolder 인가 정보를 저장한다 */
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        try {
            Map<String, Object> claims = verifyJws(request);
            setAuthenticationToContext(claims);
        } catch (SignatureException se) {
            request.setAttribute("exception", se);
        } catch (ExpiredJwtException ee) {
            request.setAttribute(
                    "exception",
                    new ServiceLogicException(ErrorCode.EXPIRED_ACCESS_TOKEN));
        } catch (Exception e) {
            request.setAttribute("exception", e);
        }
        filterChain.doFilter(request, response);
    }
    @Override
    protected boolean shouldNotFilter(
            HttpServletRequest request
    ) throws ServletException {
        String authorization = request.getHeader("Authorization");

        return authorization == null || !authorization.startsWith("Bearer ");
    }

    private Map<String, Object> verifyJws(HttpServletRequest request) {
        String jws = request.getHeader("Authorization").replace("Bearer ", "");
        String base64SecretKey = jwtTokenizer.encodeBase64SecretKey(jwtTokenizer.getSecretKey());

        return jwtTokenizer.getClaims(jws, base64SecretKey).getBody();
    }



    private void setAuthenticationToContext(Map<String ,Object> claims) {
        String username = (String) claims.get("username");
        List<GrantedAuthority> roles = authorityUtils.createAuthorities((List<String>) claims.get("roles"));
        Authentication authentication =
                new UsernamePasswordAuthenticationToken(username, null, roles);

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }


}
