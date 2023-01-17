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
    void getUser() {
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
    @DisplayName("이메일 중복 검증 TEST - USER EMIL EXISTS EXCEPTION")
    void verifyUserByEmail() {
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
    @DisplayName("로그인 타입에 따른 회원 설정 TEST")
    void checkLoginType() {
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

}