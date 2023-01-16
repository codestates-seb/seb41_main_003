package com.mainproject.server.tutoring.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class TutoringPostDto {

    @NotNull
    private Long tutorId;

    @NotNull
    private Long tuteeId;

    @NotBlank
    private String tutoringTitle;

    @NotNull
    private Long messageRoomId;
}