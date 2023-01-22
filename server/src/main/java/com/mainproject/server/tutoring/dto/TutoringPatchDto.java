package com.mainproject.server.tutoring.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class TutoringPatchDto {

    private String tutoringTitle;

    private String tutoringStatus;
}
