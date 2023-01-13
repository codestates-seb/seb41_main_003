package com.mainproject.server.review.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class ReviewResponseDto {

    private Long reviewId;

    private int professional;

    private int readiness;

    private int explanation;

    private int punctuality;

    private String reviewBody;

    private String tuteeName;

    private LocalDateTime createAt;

    private LocalDateTime updateAt;

}
