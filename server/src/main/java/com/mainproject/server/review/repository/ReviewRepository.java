package com.mainproject.server.review.repository;

import com.mainproject.server.profile.entity.Profile;
import com.mainproject.server.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findAllByProfile(Profile profile);
}
