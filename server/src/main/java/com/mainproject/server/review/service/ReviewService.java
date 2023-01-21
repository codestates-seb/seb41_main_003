package com.mainproject.server.review.service;

import com.mainproject.server.constant.ErrorCode;
import com.mainproject.server.constant.TutoringStatus;
import com.mainproject.server.exception.ServiceLogicException;
import com.mainproject.server.profile.entity.Profile;
import com.mainproject.server.review.entity.Review;
import com.mainproject.server.review.repository.ReviewRepository;
import com.mainproject.server.tutoring.entity.Tutoring;
import com.mainproject.server.tutoring.service.TutoringService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Transactional
@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final TutoringService tutoringService;

    public Review getReview(Long tutoringId) {
        return verifiedReviewById(tutoringId);
    }

    public Review createReview(Review review, Long tutoringId) {
        Tutoring tutoring = tutoringService.verifiedTutoring(tutoringId);
        if (!tutoring.getTutoringStatus().equals(TutoringStatus.WAIT_FINISH))
            throw new ServiceLogicException(ErrorCode.TUTORING_STATUS_NOT_WAIT_FINISH);
        tutoring.setTutoringStatus(TutoringStatus.FINISH);
        Profile tutee = tutoring.getTutee();
        Profile tutor = tutoring.getTutor();
        review.addTutor(tutor);
        review.addTutee(tutee);
        tutoring.addReview(review);
        setRate(tutor);
        return reviewRepository.save(review);
    }

    public Review updateReview(Review review, Long tutoringId) {
        Tutoring tutoring = tutoringService.verifiedTutoring(tutoringId);
        Review findReview = Optional.ofNullable(tutoring.getReview())
                .orElseThrow(
                        () -> new ServiceLogicException(ErrorCode.REVIEW_NOT_FOUND)
                );
        Review updateReview = updateReviewField(review, findReview);
        return reviewRepository.save(updateReview);
    }


    public void setRate(Profile tutor) {
        Set<Review> reviewSet = tutor.getReviews();
        if (!reviewSet.isEmpty()) {
            List<Review> reviews = new ArrayList<>(reviewSet);
            double rate = reviews.stream()
                    .map(review -> (review.getProfessional() +
                            review.getPunctuality() +
                            review.getExplanation() +
                            review.getReadiness()) / 4)
                    .mapToInt(i -> i)
                    .average()
                    .orElseThrow(
                            () -> new ServiceLogicException(ErrorCode.REVIEW_NOT_FOUND)
                    );
            double countRate = Math.round(rate * 2) / 2.0;
            tutor.setRate(countRate);
        }
    }

    public Review verifiedReviewById(Long reviewId) {
        return reviewRepository.findById(reviewId)
                .orElseThrow(
                        () -> new ServiceLogicException(ErrorCode.REVIEW_NOT_FOUND)
                );
    }

    public Review updateReviewField(Review updateReview, Review findReview) {
        Optional.of(updateReview.getProfessional())
                .ifPresent(findReview::setProfessional);
        Optional.of(updateReview.getExplanation())
                .ifPresent(findReview::setExplanation);
        Optional.of(updateReview.getPunctuality())
                .ifPresent(findReview::setPunctuality);
        Optional.of(updateReview.getReadiness())
                .ifPresent(findReview::setReadiness);
        Optional.ofNullable(updateReview.getReviewBody())
                .ifPresent(findReview::setReviewBody);
        return findReview;
    }
}
