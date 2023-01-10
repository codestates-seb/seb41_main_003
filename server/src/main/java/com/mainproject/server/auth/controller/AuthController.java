package com.mainproject.server.auth.controller;


import com.mainproject.server.auth.token.JwtTokenizer;
import com.mainproject.server.dto.AuthSuccessTokenResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@Validated
@Slf4j
public class AuthController {

    private final JwtTokenizer jwtTokenizer;

    @GetMapping("/verify-user")
    public ResponseEntity verifyUser(
            HttpServletRequest request
    ) throws IOException {
        String authorization = request.getHeader("Authorization");
        String accessToken = authorization.replace("Bearer ", "");
        jwtTokenizer.verifyAccessToken(accessToken);
        log.info("# Verify Login User");
        return ResponseEntity.ok().build();
    }

    @GetMapping("/reissue-token")
    public ResponseEntity reIssueToken(
            HttpServletRequest request,
            HttpServletResponse response,
            Principal principal
    ) throws IOException {
        jwtTokenizer.verifyRefreshToken(
                request.getHeader("Refresh"),
                principal.getName(),
                response);
        log.info("# Reissue Token");
        return new ResponseEntity<>(AuthSuccessTokenResponseDto.of(response), HttpStatus.OK);
    }

    @GetMapping("/logout")
    public ResponseEntity logout(
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request,response,authentication);
        }
        jwtTokenizer.deleteRefresh(request.getHeader("Authorization"));
        log.info("# User Logout");
        return ResponseEntity.ok().build();
    }

}
