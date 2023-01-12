package com.mainproject.server.dateNotice.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@Setter
public class DateNoticePatchDto {

    @NotBlank
    private Long dateNoticeId;

    @NotBlank
    private String dateNoticeTitle;

    @NotBlank
    private String startTime;

    @NotBlank
    private String endTime;

    private String scheduleBody;

    private String noticeBody;

    private List<HomeworkDto> Homeworks;
}
