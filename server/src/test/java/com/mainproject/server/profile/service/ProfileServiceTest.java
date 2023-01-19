package com.mainproject.server.profile.service;

import com.mainproject.server.constant.ErrorCode;
import com.mainproject.server.constant.ProfileStatus;
import com.mainproject.server.constant.UserStatus;
import com.mainproject.server.constant.WantedStatus;
import com.mainproject.server.exception.ServiceLogicException;
import com.mainproject.server.profile.dto.ProfilePageDto;
import com.mainproject.server.profile.dto.WantedDto;
import com.mainproject.server.profile.entity.Profile;
import com.mainproject.server.profile.repository.ProfileRepository;
import com.mainproject.server.review.entity.Review;
import com.mainproject.server.review.repository.ReviewRepository;
import com.mainproject.server.subject.dto.SubjectDto;
import com.mainproject.server.subject.repository.SubjectProfileRepository;
import com.mainproject.server.subject.repository.SubjectRepository;
import com.mainproject.server.user.entity.User;
import com.mainproject.server.user.repository.UserRepository;
import com.mainproject.server.utils.StubData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;

@ExtendWith(MockitoExtension.class)
class ProfileServiceTest {

    @Mock
    private ProfileRepository profileRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private SubjectRepository subjectRepository;

    @Mock
    private SubjectProfileRepository subjectProfileRepository;

    @Mock
    private ReviewRepository reviewRepository;

    @InjectMocks
    private ProfileService profileService;

    @Test
    @DisplayName("특정 회원 프로필 리스트 조회 TEST - 정상 동작 확인")
    void givenUserWhenProfileListSize2ThenReturnArrayListSize2() {
        // Given
        Long userId = 1L;
        User findUser = User
                .builder()
                .profiles(Set.of(new Profile(), new Profile()))
                .build();
        given(userRepository.findById(anyLong())).willReturn(Optional.of(findUser));
        // When
        List<Profile> profiles = profileService.getProfiles(userId);
        // Then
        assertThat(profiles.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("특정 프로필 상세 조회 TEST - Review 페이지 네이션 및 정상 동작 확인")
    void givenProfileWhenReviewListSize2AndProfileFieldThenReturnDtoEqualsReviewList2SizeAndProfileField() {
        // Given
        Long profileId = 1L;
        Profile profile = StubData.createProfile(profileId);
        Pageable pageable = PageRequest.of(0, 10);
        Page<Review> pageList= new PageImpl<>(
                new ArrayList<>(profile.getReviews()), pageable, profile.getReviews().size());
        given(reviewRepository.findAllByTutor(any(Profile.class), any(Pageable.class)))
                .willReturn(pageList);
        given(profileRepository.findById(anyLong())).willReturn(Optional.of(profile));
        // When
        ProfilePageDto dto = profileService.getProfile(profileId, pageable);
        // Then
        assertThat(dto.getReviews().getContent().size()).isEqualTo(2);
        assertThat(dto.getReviews().getTotalElements()).isEqualTo(2);
        assertThat(dto.getReviews().getNumber()).isEqualTo(0);
        assertThat(dto.getProfileStatus()).isEqualTo(ProfileStatus.TUTOR.name());
        assertThat(dto.getWantedStatus()).isEqualTo(WantedStatus.REQUEST.name());
        assertThat(dto.getName()).isEqualTo("test");
    }

    @Test
    @DisplayName("프로필 생성 TEST - USER TYPE NOT NONE")
    void givenUserStatusNoneWhenThrowUserTypeNotNoneThenEqualsServiceLogicException() {
        // Given
        Long userId = 1L;
        Profile profile = StubData.createProfile(1L);
        List<SubjectDto> dtoList = List.of(
                StubData.createSubjectDto(1L),
                StubData.createSubjectDto(2L));
        Pageable pageable = PageRequest.of(0, 10);
        User findUser = User.builder()
                .userStatus(UserStatus.NONE)
                .build();
        given(userRepository.findById(anyLong())).willReturn(Optional.of(findUser));
        // When
        Throwable throwable = catchThrowable(
                () -> profileService.createProfile(userId, profile, dtoList, pageable)
        );
        // Then
        assertThat(throwable)
                .isInstanceOf(ServiceLogicException.class)
                .hasMessageContaining(ErrorCode.USER_TYPE_NOT_NONE.getMessage());
    }

    @Test
    @DisplayName("프로필 생성 TEST - EXCEEDED MAXIMUM PROFILE COUNT")
    void givenUserProfileSize4WhenThrowExceededMaximumProfileCountThenEqualsServiceLogicException() {
        // Given
        Long userId = 1L;
        Profile profile = StubData.createProfile(1L);
        List<SubjectDto> dtoList = List.of(
                StubData.createSubjectDto(1L),
                StubData.createSubjectDto(2L));
        Pageable pageable = PageRequest.of(0, 10);
        User findUser = User.builder()
                .userStatus(UserStatus.TUTOR)
                .profiles(Set.of(
                        StubData.createProfile(1L),
                        StubData.createProfile(2L),
                        StubData.createProfile(3L),
                        StubData.createProfile(4L)
                ))
                .build();
        given(userRepository.findById(anyLong())).willReturn(Optional.of(findUser));
        // When
        Throwable throwable = catchThrowable(
                () -> profileService.createProfile(userId, profile, dtoList, pageable)
        );
        // Then
        assertThat(throwable)
                .isInstanceOf(ServiceLogicException.class)
                .hasMessageContaining(ErrorCode.EXCEEDED_MAXIMUM_PROFILE_COUNT.getMessage());
    }

    @Test
    @DisplayName("특정 프로필 수정 TEST")
    void givenPatchProfileWhenUpdateProfileLogicThenReturnUpdateProfile() {
        // Given
        Long id = 1L;
        Profile profile = StubData.createProfile(id);
        profile.setName("patchName");
        profile.setBio("patchBio");
        List<SubjectDto> subjectDto = List.of(
                StubData.createSubjectDto(id),
                StubData.createSubjectDto(id));
        Profile findProfile = StubData.createProfile(id);
        Pageable pageable = PageRequest.of(0, 10);
        Page<Review> pageList= new PageImpl<>(
                new ArrayList<>(profile.getReviews()), pageable, 10);
        given(reviewRepository.findAllByTutor(any(Profile.class), any(Pageable.class)))
                .willReturn(pageList);
        given(subjectRepository.findById(anyLong()))
                .willReturn(Optional.of(StubData.createSubject(id)));
        given(profileRepository.findById(anyLong()))
                .willReturn(Optional.of(findProfile));
        doNothing().when(subjectProfileRepository).deleteByProfileProfileId(anyLong());
        // When
        ProfilePageDto updateProfile =
                profileService.updateProfile(id, profile, subjectDto, pageable);
        // Then
        assertThat(updateProfile.getName()).isEqualTo(profile.getName());
        assertThat(updateProfile.getBio()).isEqualTo(profile.getBio());
    }

    @Test
    @DisplayName("특정 프로필 공고 상태 수정 TEST")
    void givenWantedStatusRequestWhenFindProfileWantedStatusNoneThenReturnUpdateProfile() {
        // Given
        Long profileId = 1L;
        WantedDto wantedDto = new WantedDto();
        wantedDto.setWantedStatus("request");
        Profile profile = StubData.createProfile(profileId);
        profile.setWantedStatus(WantedStatus.NONE);
        Pageable pageable = PageRequest.of(0, 10);
        Page<Review> pageList= new PageImpl<>(
                new ArrayList<>(profile.getReviews()), pageable, 10);
        given(reviewRepository.findAllByTutor(any(Profile.class), any(Pageable.class)))
                .willReturn(pageList);
        given(profileRepository.findById(anyLong())).willReturn(Optional.of(profile));
        // When
        ProfilePageDto updateProfile =
                profileService.updateWantedStatus(
                        profileId,
                        wantedDto,
                        pageable);
        // Then
        assertThat(updateProfile.getWantedStatus()).isEqualTo("REQUEST");
    }

    @Test
    @DisplayName("유틸 메소드 createSubjectProfile TEST")
    void givenProfileAndSubjectDtoListWhenSubjectDtoListNotNullThenReturnUpdateProfile() {
        // Given
        Long id = 1L;
        Profile profile = StubData.createProfile(id);
        List<SubjectDto> subjectDto = List.of(
                StubData.createSubjectDto(id),
                StubData.createSubjectDto(id));
        given(subjectRepository.findById(anyLong()))
                .willReturn(Optional.of(StubData.createSubject(id)));
        // When
        Profile updateProfile = profileService.createSubjectProfile(profile, subjectDto);
        // Then
        assertThat(updateProfile.getSubjectString()).contains("test,test");
    }

    @Test
    @DisplayName("프로필 존재 유무 검증 TEST - PROFILE NOT FOUND")
    void givenNullWhenThrowProfileNotFoundThenEqualsServiceLogicException() {
        // Given
        Long profileId = 1L;
        given(profileRepository.findById(anyLong())).willReturn(Optional.empty());
        // When
        Throwable throwable = catchThrowable(
                () -> profileService.verifiedProfileById(profileId)
        );
        // Then
        assertThat(throwable)
                .isInstanceOf(ServiceLogicException.class)
                .hasMessageContaining(ErrorCode.PROFILE_NOT_FOUND.getMessage());
    }

}