package com.mainproject.server.dateNotice.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
public class DateNoticePostDto {

    private String dateNoticeTitle;

    private String startTime;

    private String endTime;

    private String scheduleBody;
    private String noticeTitle;
    private String noticeBody;

    private List<String> Homeworks;

}
