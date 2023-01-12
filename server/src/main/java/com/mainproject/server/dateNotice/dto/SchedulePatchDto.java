package com.mainproject.server.dateNotice.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class SchedulePatchDto {

    @NotNull
    private Long scheduleId;

    private String scheduleTitle;

    private String scheduleBody;
}
