package com.mainproject.server.user.service;

import com.mainproject.server.auth.token.JwtAuthorityUtils;
import com.mainproject.server.constant.ErrorCode;
import com.mainproject.server.constant.LoginType;
import com.mainproject.server.constant.UserStatus;
import com.mainproject.server.exception.ServiceLogicException;
import com.mainproject.server.user.entity.User;
import com.mainproject.server.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder encoder;

    @Mock
    private JwtAuthorityUtils utils;

    @InjectMocks
    private UserService userService;

    @Test
    @DisplayName("회원 조회 TEST - USER NOT FOUND EXCEPTION")
    void  givenNullWhenThrowExceptionThenEqualServiceLogicException() {
        // Given
        Long userId = 1L;
        given(userRepository.findById(anyLong())).willReturn(Optional.empty());
        // When
        Throwable throwable = catchThrowable(
                () -> userService.getUser(userId)
        );
        // Then
        assertThat(throwable)
                .isInstanceOf(ServiceLogicException.class)
                .hasMessageContaining(ErrorCode.USER_NOT_FOUND.getMessage());
    }

    @Test
    @DisplayName("이메일 중복 검증 TEST - USER EMIL EXISTS")
    void givenUserWhenThrowUserEmailExistsThenEqualsServiceLogicException() {
        // Given
        String email = "test@test.com";
        given(userRepository.findByEmail(anyString())).willReturn(Optional.of(new User()));
        // When
        Throwable throwable = catchThrowable(
                () -> userService.verifyUserByEmail(email)
        );
        // Then
        assertThat(throwable)
                .isInstanceOf(ServiceLogicException.class)
                .hasMessageContaining(ErrorCode.USER_EMAIL_EXISTS.getMessage());
    }

    @Test
    @DisplayName("이메일로 회원 조회 TEST - USER NOT FOUND")
    void givenNullWhenThrowUserNotFoundThenEqualsServiceLogicException() {
        // Given
        String email = "test@test.com";
        given(userRepository.findByEmail(anyString())).willReturn(Optional.empty());
        // When
        Throwable throwable = catchThrowable(
                () -> userService.verifiedUserByEmail(email)
        );
        // Then
        assertThat(throwable)
                .isInstanceOf(ServiceLogicException.class)
                .hasMessageContaining(ErrorCode.USER_NOT_FOUND.getMessage());
    }

    @Test
    @DisplayName("휴대폰 번호 중복 검증 TEST - PHONE NUMBER EXISTS")
    void givenUserWhenThrowPhoneNumberExistsThenEqualsServiceLogicException() {
        // Given
        Long userId = 1L;
        String phoneNumber = "010-0000-0000";
        given(userRepository.findByPhoneNumber(anyString()))
                .willReturn(Optional.of(User.builder().userId(2L).build()));
        // When
        Throwable throwable = catchThrowable(
                () -> userService.verifyPhoneNumber(phoneNumber, userId)
        );
        // Then
        assertThat(throwable)
                .isInstanceOf(ServiceLogicException.class)
                .hasMessageContaining(ErrorCode.PHONE_NUMBER_EXISTS.getMessage());
    }

    @Test
    @DisplayName("2차 비밀번호 검증 TEST - WRONG SECOND PASSWORD")
    void givenSecondPasswordWhenThrowWrongSecondPasswordThenEqualsServiceLogicException() {
        // Given
        String secondPassword = "1111!";
        Long userId = 1L;
        User user = User.builder().secondPassword("2222!").build();
        given(userRepository.findById(anyLong())).willReturn(Optional.of(user));
        // When
        Throwable throwable = catchThrowable(
                () -> userService.verifySecondPassword(userId, secondPassword)
        );
        // Then
        assertThat(throwable)
                .isInstanceOf(ServiceLogicException.class)
                .hasMessageContaining(ErrorCode.WRONG_SECOND_PASSWORD.getMessage());
    }

    @Test
    @DisplayName("로그인 타입에 따른 회원 설정 TEST")
    void givenNullUserAndSocialUserWhenCheckLoginTypeThenLoginTypeBasicAndSocial() {
        // Given
        User nullUser = User.builder()
                .email("test@test.com")
                .password("1111!")
                .loginType(null)
                .build();
        User socialUser = User.builder()
                .email("test1@test.com")
                .password("1111!")
                .loginType(LoginType.SOCIAL)
                .build();
        given(encoder.encode(anyString())).willReturn("test");
        given(utils.createRoles(anyString())).willReturn(JwtAuthorityUtils.USER_ROLES_STRING_CALL);
        // When
        User nullUserTest = userService.checkLoginType(nullUser);
        User socialUserTest = userService.checkLoginType(socialUser);
        // Then
        assertThat(nullUserTest.getPassword()).isEqualTo("test");
        assertThat(nullUserTest.getUserStatus()).isEqualTo(UserStatus.NONE);
        assertThat(nullUserTest.getLoginType()).isEqualTo(LoginType.BASIC);
        assertThat(nullUserTest.getRoles()).isEqualTo(JwtAuthorityUtils.USER_ROLES_STRING_CALL);
        assertThat(socialUserTest.getPassword()).isEqualTo("test");
        assertThat(socialUserTest.getUserStatus()).isNull();
    }

    @Test
    @DisplayName("회원 상태에 따른 예외 처리 검증 TEST - USER TYPE NOT NONE")
    void givenUserStatusNoneWhenThrowUserTypeNotNoneThenEqualsServiceLogicException() {
        // Given
        User postUser = User.builder()
                .userStatus(UserStatus.NONE)
                .build();
        User findUser = User.builder()
                .userStatus(UserStatus.NONE)
                .build();
        // When
        Throwable throwable = catchThrowable(
                () -> userService.verifyUserStatusAndCreateBasicProfile(postUser, findUser)
        );
        // Then
        assertThat(throwable)
                .isInstanceOf(ServiceLogicException.class)
                .hasMessageContaining(ErrorCode.USER_TYPE_NOT_NONE.getMessage());
    }

    @Test
    @DisplayName("회원 상태에 따른 예외 처리 검증 TEST - NOT CHANGE USER STATUS")
    void givenUserStatusTutorAndTuteeWhenThrowUserTypeNotNoneThenEqualsServiceLogicException() {
        // Given
        User postUser = User.builder()
                .userStatus(UserStatus.TUTOR)
                .build();
        User findUser = User.builder()
                .userStatus(UserStatus.TUTEE)
                .build();
        // When
        Throwable throwable = catchThrowable(
                () -> userService.verifyUserStatusAndCreateBasicProfile(postUser, findUser)
        );
        // Then
        assertThat(throwable)
                .isInstanceOf(ServiceLogicException.class)
                .hasMessageContaining(ErrorCode.NOT_CHANGE_USER_STATUS.getMessage());
    }


}