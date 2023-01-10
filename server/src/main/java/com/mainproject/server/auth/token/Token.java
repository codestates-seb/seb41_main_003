package com.mainproject.server.auth.token;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@NoArgsConstructor
@Getter
@AllArgsConstructor
public class Token {
    private String accessToken;
    private String refreshToken;
}
