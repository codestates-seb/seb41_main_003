package com.mainproject.server.dateNotice.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class HomeworkPatchDto {

    @NotNull
    private Long homeworkId;

    @NotNull
    private String homeworkBody;

    @NotBlank
    private String homeworkStatus;
}
