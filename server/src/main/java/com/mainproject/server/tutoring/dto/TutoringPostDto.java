package com.mainproject.server.tutoring.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class TutoringPostDto {

    @NotBlank
    private Long tutorId;

    @NotBlank
    private Long tuteeId;

    @NotBlank
    private String tutoringTitle;
}
