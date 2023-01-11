package com.mainproject.server.auth.controller;


import com.mainproject.server.auth.dto.PasswordDto;
import com.mainproject.server.auth.token.JwtTokenizer;
import com.mainproject.server.dto.AuthSuccessTokenResponseDto;
import com.mainproject.server.user.entity.User;
import com.mainproject.server.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@Validated
@Slf4j
public class AuthController {

    private final JwtTokenizer jwtTokenizer;

    private final UserService userService;

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

    @GetMapping("/reissue-token/{userId}")
    public ResponseEntity reIssueToken(
            HttpServletResponse response,
            @PathVariable Long userId
    ) throws IOException {
        User user = userService.verifiedUserById(userId);
        jwtTokenizer.verifyRefreshToken(user.getEmail(), response);
        response.setHeader("userStatus", user.getUserStatus().name());
        response.setHeader("userId", user.getUserId().toString());
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
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }
        jwtTokenizer.deleteRefresh(request.getHeader("Authorization"));
        log.info("# User Logout");
        return ResponseEntity.ok().build();
    }

    @PostMapping("/verify-second-password/{userId}")
    public ResponseEntity verifySecondPassword(
            @RequestBody PasswordDto dto,
            @PathVariable Long userId
    ) {
        userService.verifySecondPassword(userId, dto.getSecondPassword());
        return ResponseEntity.ok().build();
    }

}
