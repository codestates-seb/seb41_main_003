package com.mainproject.server.dateNotice.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
public class HomeworkPostDto {

    @NotBlank
    private String homeworkBody;

    @NotBlank
    private String homeworkStatus;
}
