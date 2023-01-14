package com.mainproject.server.tutoring.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class TutoringPatchDto {

    private Long tutoringId;

    @NotBlank
    private String tutoringTitle;
}
