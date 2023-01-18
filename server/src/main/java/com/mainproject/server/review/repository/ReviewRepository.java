package com.mainproject.server.review.repository;

import com.mainproject.server.profile.entity.Profile;
import com.mainproject.server.review.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    Page<Review> findAllByTutor(Profile tutor, Pageable pageable);
}
