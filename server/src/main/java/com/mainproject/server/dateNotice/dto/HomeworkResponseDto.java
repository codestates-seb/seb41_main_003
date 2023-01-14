package com.mainproject.server.dateNotice.dto;

import com.mainproject.server.dateNotice.entity.Homework;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HomeworkResponseDto {

    private Long homeworkId;

    private String homeworkBody;

    private String HomeworkStatus;

    public HomeworkResponseDto(Homework homework) {
        this.homeworkId = homework.getHomeworkId();
        this.homeworkBody = homework.getHomeworkBody();
        this.HomeworkStatus = homework.getHomeworkStatus().name();
    }

    public static HomeworkResponseDto of(Homework homework) {
        return new HomeworkResponseDto(homework);
    }
}
