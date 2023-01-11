package com.mainproject.server.user.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class UserPatchDto {

    private Long userId;

    private String nickName;

    private String password;

    @NotBlank
    private String phoneNumber;

    @NotBlank
    private String secondPassword;

    @NotBlank
    private String userStatus;

}
