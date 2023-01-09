package com.mainproject.server.review.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewPostDto {
    private Long profileId;
    private Long tutoringId;
    private int professional;
    private int readiness;
    private int explanation;
    private int punctuality;
    private String reviewBody;
}
