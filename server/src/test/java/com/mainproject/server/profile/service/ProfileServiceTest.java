package com.mainproject.server.profile.service;

import com.mainproject.server.constant.ErrorCode;
import com.mainproject.server.constant.ProfileStatus;
import com.mainproject.server.constant.UserStatus;
import com.mainproject.server.constant.WantedStatus;
import com.mainproject.server.exception.ServiceLogicException;
import com.mainproject.server.image.entity.ProfileImage;
import com.mainproject.server.profile.dto.ProfilePageDto;
import com.mainproject.server.profile.entity.Profile;
import com.mainproject.server.profile.repository.ProfileRepository;
import com.mainproject.server.review.entity.Review;
import com.mainproject.server.subject.dto.SubjectDto;
import com.mainproject.server.subject.entity.Subject;
import com.mainproject.server.subject.entity.SubjectProfile;
import com.mainproject.server.subject.repository.SubjectProfileRepository;
import com.mainproject.server.subject.repository.SubjectRepository;
import com.mainproject.server.user.entity.User;
import com.mainproject.server.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

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
        Profile profile = createProfile(profileId);
        Pageable pageable = PageRequest.of(0, 10);
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
        Profile profile = createProfile(1L);
        List<SubjectDto> dtoList = List.of(createSubjectDto(1L),createSubjectDto(2L));
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

        // When

        // Then
    }

    public SubjectDto createSubjectDto(Long id) {
        return new SubjectDto(id, "test", "test");
    }

    public Review createReview(Long id) {
        Review get = Review.builder()
                .reviewId(id)
                .professional(4)
                .readiness(5)
                .explanation(5)
                .punctuality(4)
                .reviewBody("test")
                .tutor(Profile.builder().name("test").build())
                .tutee(Profile.builder().name("test").build())
                .build();
        get.setCreateAt(LocalDateTime.now());
        get.setUpdateAt(LocalDateTime.now());
        return get;
    }

    public Subject createSubject(Long id) {
        Subject get = Subject.builder()
                .subjectId(id)
                .subjectTitle("test")
                .build();
        get.setCreateAt(LocalDateTime.now());
        get.setUpdateAt(LocalDateTime.now());
        return get;
    }

    public Profile createProfile(Long id) {
        Profile get = Profile.builder()
                .profileId(id)
                .name("test")
                .rate(4.5)
                .bio("test")
                .wantDate("test")
                .pay("test")
                .way("test")
                .profileStatus(ProfileStatus.TUTOR)
                .wantedStatus(WantedStatus.REQUEST)
                .gender("test")
                .school("test")
                .character("test")
                .preTutoring("test")
                .difference("test")
                .subjectString("test")
                .subjectProfiles(Set.of(createSubjectProfile(id), createSubjectProfile(id)))
                .reviews(Set.of(createReview(id), createReview(id)))
                .profileImage(createImage(id))
                .user(new User())
                .build();
        get.setCreateAt(LocalDateTime.now());
        get.setUpdateAt(LocalDateTime.now());
        return get;
    }

    public ProfileImage createImage(Long id) {
        ProfileImage get = ProfileImage.builder()
                .profileImageId(id)
                .fileName("test")
                .url("test")
                .build();
        get.setCreateAt(LocalDateTime.now());
        get.setUpdateAt(LocalDateTime.now());
        return get;
    }

    public SubjectProfile createSubjectProfile(Long id) {
        return SubjectProfile.builder()
                .profile(Profile.builder().profileId(id).build())
                .subject(createSubject(id))
                .content("test")
                .subjectProfileId(id)
                .build();
    }

}