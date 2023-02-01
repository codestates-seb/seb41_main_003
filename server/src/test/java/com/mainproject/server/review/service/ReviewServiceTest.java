package com.mainproject.server.review.service;

import com.mainproject.server.constant.ErrorCode;
import com.mainproject.server.exception.ServiceLogicException;
import com.mainproject.server.profile.entity.Profile;
import com.mainproject.server.review.entity.Review;
import com.mainproject.server.review.repository.ReviewRepository;
import com.mainproject.server.tutoring.service.TutoringService;
import com.mainproject.server.utils.StubData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ReviewServiceTest {

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private TutoringService tutoringService;

    @InjectMocks
    private ReviewService reviewService;

    @Test
    @DisplayName("Review ID 검증 메소드 TEST - REVIEW NOT FOUND")
    void givenNullWhenVerifiedReviewByIdThrowExceptionThenEqualsServiceLogicException() {
        // Given
        Long reviewId = 1L;
        given(reviewRepository.findById(anyLong())).willReturn(Optional.empty());
        // When
        Throwable throwable = catchThrowable(() -> reviewService.verifiedReviewById(reviewId));
        // Then
        assertThat(throwable)
                .isInstanceOf(ServiceLogicException.class)
                .hasMessageContaining(ErrorCode.REVIEW_NOT_FOUND.getMessage());
    }

    @Test
    @DisplayName("Review 등록시 Rate 설정 알고리즘 TEST")
    void givenProfileWhenSetRateThenUpdateProfileRate() {
        // Given
        Profile profile = StubData.createProfile(1L);
        // When
        reviewService.setRate(profile);
        // Then
        assertThat(profile.getRate()).isEqualTo(4.5);
    }

    @Test
    @DisplayName("Review 수정 로직 TEST")
    void givenUpdateReviewAndFindReviewWhenUpdateReviewFieldThenReturnUpdateReview() {
        // Given
        Review updateReview = StubData.createReview(1L);
        Review findReview = StubData.createReview(2L);
        updateReview.setProfessional(5);
        updateReview.setExplanation(5);
        updateReview.setPunctuality(5);
        updateReview.setReadiness(5);
        updateReview.setReviewBody("Test Body");
        // When
        Review resultReview = reviewService.updateReviewField(updateReview, findReview);
        // Then
        assertThat(resultReview.getProfessional()).isEqualTo(updateReview.getProfessional());
        assertThat(resultReview.getExplanation()).isEqualTo(updateReview.getExplanation());
        assertThat(resultReview.getPunctuality()).isEqualTo(updateReview.getPunctuality());
        assertThat(resultReview.getReadiness()).isEqualTo(updateReview.getReadiness());
        assertThat(resultReview.getReviewBody()).isEqualTo(updateReview.getReviewBody());
    }

}