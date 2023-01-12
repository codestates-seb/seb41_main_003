package com.mainproject.server.tutoring.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class TutoringSimpleResponseDto {

    private Long tutoringId;

    private String tutorName;

    private String tuteeName;

    private String tutoringTitle;

    private String tutoringStatus;

    private LocalDateTime createAt;

    private LocalDateTime updateAt;
}
