package com.mainproject.server.user.dto;


import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserResponseDto {

    private Long userId;

    private String email;

    private String phoneNumber;

    private String loginType;

    private String userStatus;

    private LocalDateTime createAt;

    private LocalDateTime updateAt;

}
