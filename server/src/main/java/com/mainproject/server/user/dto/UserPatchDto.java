package com.mainproject.server.user.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class UserPatchDto {

    private Long userId;

    @NotBlank
    private String nickName;

    private String password;

    @NotBlank
    @Pattern(regexp = "\\d{3}-\\d{4}-\\d{4}",message = "휴대폰 번호를 올바르게 입력해 주세요")
    private String phoneNumber;

    private String secondPassword;

    private String userStatus;

}
