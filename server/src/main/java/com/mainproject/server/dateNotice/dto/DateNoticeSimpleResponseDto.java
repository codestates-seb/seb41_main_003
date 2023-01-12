package com.mainproject.server.dateNotice.dto;

import com.mainproject.server.constant.HomeworkStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DateNoticeSimpleResponseDto {
    private Long dateNoticeId;

    private String dateNoticeTitle;

    private String startTime;

    private String endTime;

    private int homeworkCount;

    private int finishHomeworkCount;

    private String noticeStatus;

    public DateNoticeSimpleResponseDto(DateNoticeResponseDto dto) {
        this.dateNoticeId = dto.getDateNoticeId();
        this.dateNoticeTitle = dto.getDateNoticeTitle();
        this.startTime = dto.getStartTime();
        this.endTime = dto.getEndTime();
        this.homeworkCount = dto.getHomeworks().size();
        this.finishHomeworkCount = (int) dto.getHomeworks().stream()
                .filter(h ->
                        h.getHomeworkStatus()
                                .equals(HomeworkStatus.FINISH.name()))
                .count();
        this.noticeStatus = dto.getNoticeStatus();
    }

    public static DateNoticeSimpleResponseDto of(DateNoticeResponseDto dto) {
        return new DateNoticeSimpleResponseDto(dto);
    }
}
