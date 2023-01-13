package com.mainproject.server.dateNotice.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
public class DateNoticePatchDto {

    @NotNull
    private Long dateNoticeId;

    @NotBlank
    private String dateNoticeTitle;

    @NotBlank
    private String startTime;

    @NotBlank
    private String endTime;

    @NotBlank
    private String scheduleBody;

    @NotNull
    private String noticeBody;

    private List<HomeworkPatchDto> Homeworks;
}
