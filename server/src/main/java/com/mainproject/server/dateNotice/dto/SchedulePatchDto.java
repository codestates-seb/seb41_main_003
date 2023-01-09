package com.mainproject.server.dateNotice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SchedulePatchDto {
    private Long scheduleId;
    private String scheduleTitle;
    private String scheduleBody;
}
