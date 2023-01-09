package com.mainproject.server.user.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class UserPatchDto {

    private Long userId;

    private String nickName;

    private String password;

    private String secondPassword;

    private String userStatus;

}
