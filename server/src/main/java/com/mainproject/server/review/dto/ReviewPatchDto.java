package com.mainproject.server.review.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class ReviewPatchDto {

    @NotNull
    private Long reviewId;

    @Min(1) @Max(5)
    private int professional;

    @Min(1) @Max(5)
    private int readiness;

    @Min(1) @Max(5)
    private int explanation;

    @Min(1) @Max(5)
    private int punctuality;

    private String reviewBody;
}
