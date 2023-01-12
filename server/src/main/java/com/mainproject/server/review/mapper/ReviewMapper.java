package com.mainproject.server.review.mapper;

import com.mainproject.server.review.dto.ReviewPatchDto;
import com.mainproject.server.review.dto.ReviewPostDto;
import com.mainproject.server.review.dto.ReviewResponseDto;
import com.mainproject.server.review.entity.Review;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ReviewMapper {
    Review reviewPostDtoToReview(ReviewPostDto reviewPostDto);

    Review reviewPatchDtoToReview(ReviewPatchDto reviewPatchDto);

    @Mapping(source = "profile.name", target = "tuteeName")
    ReviewResponseDto reviewToReviewResponseDto(Review review);

}
