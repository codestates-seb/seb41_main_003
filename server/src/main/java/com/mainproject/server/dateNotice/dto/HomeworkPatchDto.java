package com.mainproject.server.dateNotice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HomeworkPatchDto {
    private Long homeworkId;
    private String homeworkBody;
    private String HomeworkStatus;
}