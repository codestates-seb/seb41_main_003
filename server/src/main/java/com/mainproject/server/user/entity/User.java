package com.mainproject.server.user.entity;

import com.mainproject.server.audit.Auditable;
import com.mainproject.server.constant.LoginType;
import com.mainproject.server.constant.UserStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@NoArgsConstructor
@Entity
public class User extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private String email;

    private String password;

    private String secondPassword;

    private String phoneNumber;

    private LoginType loginType;

    private UserStatus userStatus;
}
