package com.mainproject.server.user.service;


import com.mainproject.server.constant.ErrorCode;
import com.mainproject.server.constant.LoginType;
import com.mainproject.server.exception.ServiceLogicException;
import com.mainproject.server.user.entity.User;
import com.mainproject.server.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    public User getUser(Long userId) {
        return verifiedUserById(userId);
    }

    public User createUser(User user) {
        if (user.getLoginType().equals(LoginType.SOCIAL)) {
            // Todo 소셜 로그인 경우 임의의 password 저장
        }
        return userRepository.save(user);
    }

    public User verifiedUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ServiceLogicException(ErrorCode.USER_NOT_FOUND));

    }

    public User verifiedUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ServiceLogicException(ErrorCode.USER_NOT_FOUND));
    }

}
