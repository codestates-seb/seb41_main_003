package com.mainproject.server.tutoring.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TutoringPatchDto {

    private Long tutoringId;

    private String tutoringTitle;

    private String tutoringStatus;
}
