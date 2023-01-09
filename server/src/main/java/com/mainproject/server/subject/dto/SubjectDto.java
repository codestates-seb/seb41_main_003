package com.mainproject.server.subject.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class SubjectDto {

    @NotNull
    private Long subjectId;

    @NotBlank
    private String subjectTitle;


    private String content;
}
