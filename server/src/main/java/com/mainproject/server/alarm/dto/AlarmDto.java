package com.mainproject.server.alarm.dto;

import com.mainproject.server.alarm.entity.Alarm;
import com.mainproject.server.constant.AlarmStatus;
import com.mainproject.server.constant.AlarmType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlarmDto {

    Long alarmId;

    String profileName;

    Long contentId;

    AlarmType alarmType;

    AlarmStatus alarmStatus;

    public AlarmDto(Alarm alarm) {
        this.alarmId = alarm.getAlarmId();
        this.profileName = alarm.getProfileName();
        this.contentId = alarm.getContentId();
        this.alarmType = alarm.getAlarmType();
        this.alarmStatus = alarm.getAlarmStatus();
    }

    public static AlarmDto of(Alarm alarm) {
        return new AlarmDto(alarm);
    }

}
