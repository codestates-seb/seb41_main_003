package com.mainproject.server.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.servlet.http.HttpServletResponse;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AuthSuccessTokenResponseDto {

    private String Authorization;

    private String  userId;

    private String userStatus;
    public static AuthSuccessTokenResponseDto of(HttpServletResponse response) {
        return new AuthSuccessTokenResponseDto(
                response.getHeader("Authorization"),
                response.getHeader("userId"),
                response.getHeader("userStatus")
        );
    }
}
