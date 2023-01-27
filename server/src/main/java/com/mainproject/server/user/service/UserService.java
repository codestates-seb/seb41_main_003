package com.mainproject.server.user.service;


import com.mainproject.server.auth.token.JwtAuthorityUtils;
import com.mainproject.server.constant.*;
import com.mainproject.server.exception.ServiceLogicException;
import com.mainproject.server.image.entity.ProfileImage;
import com.mainproject.server.image.service.ImageService;
import com.mainproject.server.profile.entity.Profile;
import com.mainproject.server.profile.service.ProfileService;
import com.mainproject.server.user.entity.User;
import com.mainproject.server.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashSet;
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

    private final ImageService imageService;

    private final ProfileService profileService;

    public User getUser(Long userId) {
        return verifiedUserById(userId);
    }

    public User createUser(User user) {
        verifyUserByEmail(user.getEmail());
        checkLoginType(user);
        return userRepository.save(user);
    }

    public User updateUser(User user) {
        if (user.getPassword() != null) {
            String encode = passwordEncoder.encode(user.getPassword());
            user.setPassword(encode);
        }
        User findUser = verifiedUserById(user.getUserId());
        verifyUserStatusAndCreateBasicProfile(user, findUser);
        Optional.ofNullable(user.getNickName())
                .ifPresent(findUser::setNickName);
        Optional.ofNullable(user.getPassword())
                .ifPresent(findUser::setPassword);
        Optional.ofNullable(user.getSecondPassword())
                .ifPresent(findUser::setSecondPassword);
        Optional.ofNullable(verifyPhoneNumber(user.getPhoneNumber(), user.getUserId()))
                .ifPresent(findUser::setPhoneNumber);
        return userRepository.save(findUser);
    }

    public void deleteUser(Long userId) {
        User findUser = verifiedUserById(userId);
        findUser.setUserStatus(UserStatus.INACTIVE);
        findUser.getProfiles()
                .forEach(p -> p.setWantedStatus(WantedStatus.NONE));
        userRepository.save(findUser);
    }


    /* 검증 로직 및 유틸 로직*/

    public void verifySecondPassword(Long userId, String secondPassword) {
        User findUser = verifiedUserById(userId);
        String comp = findUser.getSecondPassword();
        if (!secondPassword.equals(comp))
            throw new ServiceLogicException(ErrorCode.WRONG_SECOND_PASSWORD);
    }

    public String verifyPhoneNumber(String phoneNumber, Long userId) {
        Optional<User> findUser = userRepository.findByPhoneNumber(phoneNumber);
        if (findUser.isEmpty()) {
            return phoneNumber;
        } else {
            if (findUser.get().getUserId().equals(userId)) {
                return phoneNumber;
            } else {
                throw new ServiceLogicException(ErrorCode.PHONE_NUMBER_EXISTS);
            }
        }
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

    public void verifyUserStatusAndCreateBasicProfile(User postUser, User findUser) {
        UserStatus findUserStatus = findUser.getUserStatus();
        UserStatus userStatus = postUser.getUserStatus();
        if (findUserStatus.equals(UserStatus.NONE)) {
            if (userStatus == null || userStatus.equals(UserStatus.NONE)) {
                throw new ServiceLogicException(ErrorCode.USER_TYPE_NOT_NONE);
            }
            findUser.setUserStatus(userStatus);
            Profile basicProfile = createBasicProfile(findUser);
            profileService.delegateSaveProfile(basicProfile);
        } else if (findUserStatus.equals(UserStatus.TUTEE) ||
                findUserStatus.equals(UserStatus.TUTOR)
        ) {
            if (!findUserStatus.equals(userStatus)) {
                throw new ServiceLogicException(ErrorCode.NOT_CHANGE_USER_STATUS);
            }
        }
    }

    public Profile createBasicProfile(User user) {
        ProfileImage basicImage = imageService.getBasicImage();
        Profile get = Profile.builder()
                .name("프로필 수정해 주세요").rate(0.0)
                .bio("한줄 소개를 수정 해 주세요").wantDate("").pay("").way("")
                .profileStatus(ProfileStatus.valueOf(user.getUserStatus().name()))
                .wantedStatus(WantedStatus.BASIC)
                .gender("").school("").character("").preTutoring("")
                .difference("").subjectString("").reviews(new LinkedHashSet<>())
                .subjectProfiles(new LinkedHashSet<>()).build();
        get.addUser(user);
        get.addProfileImage(basicImage);
        return get;
    }

}
