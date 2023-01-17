package com.mainproject.server.user.service;


import com.mainproject.server.auth.token.JwtAuthorityUtils;
import com.mainproject.server.constant.ErrorCode;
import com.mainproject.server.constant.LoginType;
import com.mainproject.server.constant.UserStatus;
import com.mainproject.server.exception.ServiceLogicException;
import com.mainproject.server.user.entity.User;
import com.mainproject.server.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Random;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtAuthorityUtils authorityUtils;

    public User getUser(Long userId) {
        return verifiedUserById(userId);
    }

    public User createUser(User user) {
        verifyUserByEmail(user.getEmail());
        checkLoginType(user);
        return userRepository.save(user);
    }

    public User updateUser(User user) {
        User findUser = verifiedUserById(user.getUserId());
        Optional.ofNullable(user.getNickName())
                .ifPresent(findUser::setNickName);
        Optional.ofNullable(user.getPassword())
                .ifPresent(findUser::setPassword);
        Optional.ofNullable(user.getSecondPassword())
                .ifPresent(findUser::setSecondPassword);
        if (findUser.getUserStatus().equals(UserStatus.NONE)) {
            findUser.setUserStatus(user.getUserStatus());
        } else if (user.getUserStatus() == null) {

        } else {
            throw new ServiceLogicException(ErrorCode.NOT_CHANGE_USER_STATUS);
        }
        Optional.ofNullable(verifyPhoneNumber(user.getPhoneNumber()))
                .ifPresent(findUser::setPhoneNumber);
        return userRepository.save(findUser);
    }

    public void deleteUser(Long userId) {
        userRepository.delete(verifiedUserById(userId));
    }


    /* 검증 로직 */

    public void verifySecondPassword(Long userId, String secondPassword) {
        User findUser = verifiedUserById(userId);
        String comp = findUser.getSecondPassword();
        if (!secondPassword.equals(comp))
            throw new ServiceLogicException(ErrorCode.WRONG_SECOND_PASSWORD);
    }

    public String verifyPhoneNumber(String phoneNumber) {
        if (userRepository.findByPhoneNumber(phoneNumber).isPresent()) {
            throw new ServiceLogicException(ErrorCode.PHONE_NUMBER_EMAIL_EXISTS);
        }
        return phoneNumber;
    }

    public User verifiedUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() ->
                        new ServiceLogicException(ErrorCode.USER_NOT_FOUND));

    }

    public User verifiedUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ServiceLogicException(ErrorCode.USER_NOT_FOUND));
    }

    public void verifyUserByEmail(String email) {
        Optional<User> findUser = userRepository.findByEmail(email);
        if (findUser.isPresent()) {
            throw new ServiceLogicException(ErrorCode.USER_EMAIL_EXISTS);
        }
    }

    public Boolean verifyUserByEmailReturnBoolean(String email) {
        Optional<User> findUser = userRepository.findByEmail(email);
        return findUser.isPresent();
    }

    public User checkLoginType(User user) {
        if (user.getLoginType() == null) {
            String pw = passwordEncoder
                    .encode(user.getPassword());
            user.setPassword(pw);
            user.setRoles(authorityUtils.createRoles(user.getEmail()));
            user.setUserStatus(UserStatus.NONE);
            user.setLoginType(LoginType.BASIC);
        } else {
            Random random = new Random();
            String pw = passwordEncoder
                    .encode(String.valueOf(random.nextInt()));
            user.setPassword(pw);
        }
        return user;
    }

}
