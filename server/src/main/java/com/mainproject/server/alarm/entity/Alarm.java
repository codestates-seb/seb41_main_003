package com.mainproject.server.alarm.entity;

import com.mainproject.server.constant.AlarmStatus;
import com.mainproject.server.constant.AlarmType;
import com.mainproject.server.profile.entity.Profile;
import lombok.*;

import javax.persistence.*;


@Getter
@NoArgsConstructor
@Entity
@Builder
@AllArgsConstructor
public class Alarm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter
    Long alarmId;

    @Column(nullable = false)
    @Setter
    String profileName;

    @Column(nullable = false)
    @Setter
    Long contentId;

    @Column(nullable = false)
    @Setter
    AlarmType alarmType;

    @Column(nullable = false)
    @Setter
    AlarmStatus alarmStatus;

    @ToString.Exclude
    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @Setter
    private Profile profile;

    public void addProfile(Profile profile) {
        setProfile(profile);
        profile.addAlarm(this);
    }

}
