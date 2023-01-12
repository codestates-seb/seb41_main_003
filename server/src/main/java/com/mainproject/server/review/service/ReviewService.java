package com.mainproject.server.review.service;

import com.mainproject.server.constant.ErrorCode;
import com.mainproject.server.exception.ServiceLogicException;
import com.mainproject.server.profile.entity.Profile;
import com.mainproject.server.review.entity.Review;
import com.mainproject.server.review.repository.ReviewRepository;
import com.mainproject.server.tutoring.service.TutoringService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final TutoringService tutoringService;
//    private final ProfileService profileService;

    public Review findReview(Long tutoringId) {
        return findVerifiedReviewById(tutoringId);
    }

    public Review createReview(Review review, Long tutoringId) {
        tutoringService.setTutoringStatusFinish(tutoringId);

        Profile tutee = tutoringService.verifiedTutoring(tutoringId).getTutee();

        // Todo: ProfileService를 통한 profile 조회
        review.addProfile(new Profile());
        Review saveReview = reviewRepository.save(review);

        return saveReview;
    }

    public Review updateReview(Review review) {
        // profile은 안바뀌니 reviewRepository에서 tutee 정보를 받아옴
        Long reviewId = review.getReviewId();
        Review findReview = findVerifiedReviewById(reviewId);

        review.addProfile(findReview.getProfile());

        Optional.ofNullable(review.getProfessional())
                .ifPresent(findReview::setProfessional);
        Optional.ofNullable(review.getExplanation())
                .ifPresent(findReview::setExplanation);
        Optional.ofNullable(review.getPunctuality())
                .ifPresent(findReview::setPunctuality);
        Optional.ofNullable(review.getReadiness())
                .ifPresent(findReview::setReadiness);
        Optional.ofNullable(review.getReviewBody())
                .ifPresent(findReview::setReviewBody);

        return reviewRepository.save(findReview);
    }

    public void deleteReview(Long reviewId) {
        Review review = findVerifiedReviewById(reviewId);
        reviewRepository.delete(review);
    }

    // Todo: ProfileService를 통한 프로필 조회
    public double createRate(Long profileId) {
        Profile profile = new Profile();
        List<Review> reviews = reviewRepository.findAllByProfile(profile);

        double rate = reviews.stream()
                .map(review -> review.getProfessional() +
                        review.getPunctuality() +
                        review.getExplanation() +
                        review.getReadiness())
                .mapToInt(i -> i)
                .average()
                .getAsDouble();

        double averageRate = Math.round(rate * 2) / 2.0;

        return averageRate;
    }

    private Review findVerifiedReviewById(Long tutoringId) {
        Optional<Review> optionalReview = reviewRepository.findById(tutoringId);
        Review findReview = optionalReview.orElseThrow(() -> new ServiceLogicException(ErrorCode.NOT_FOUND));

        return findReview;
    }
}
