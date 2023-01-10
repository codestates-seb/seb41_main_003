package com.mainproject.server.auth.token;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtAuthorityUtils {

    public static final List<GrantedAuthority> ADMIN_ROLES = AuthorityUtils.createAuthorityList("ROLE_ADMIN", "ROLE_USER");

    public static final List<GrantedAuthority> USER_ROLES = AuthorityUtils.createAuthorityList("ROLE_USER");
    private final List<String> ADMIN_ROLES_STRING = List.of("ADMIN", "USER");
    private final List<String> USER_ROLES_STRING = List.of("USER");

    public static final List<String> ADMIN_ROLES_STRING_CALL = List.of("ADMIN", "USER");
    public static final List<String> USER_ROLES_STRING_CALL = List.of("USER");


    /* 권한 부여 메소드 */
    public List<GrantedAuthority> createAuthorities(List<String> roles) {
        List<GrantedAuthority> result =  roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_"+role))
                .collect(Collectors.toList());
        return result;
    }

    public static List<GrantedAuthority> getAuthorities(List<String> roles) {
        List<GrantedAuthority> result =  roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_"+role))
                .collect(Collectors.toList());
        return result;
    }

    /* 권한 String 생성 메소드 */
    public List<String> createRoles(String email) {
        return USER_ROLES_STRING;
    }

}