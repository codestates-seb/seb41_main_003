package com.mainproject.server.alarm.service;

import com.mainproject.server.alarm.entity.Alarm;
import com.mainproject.server.alarm.repository.AlarmRepository;
import com.mainproject.server.constant.AlarmStatus;
import com.mainproject.server.constant.AlarmType;
import com.mainproject.server.constant.ErrorCode;
import com.mainproject.server.exception.ServiceLogicException;
import com.mainproject.server.profile.entity.Profile;
import com.mainproject.server.profile.repository.ProfileRepository;
import com.mainproject.server.profile.service.ProfileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.LinkedHashSet;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class AlarmService {

    private final AlarmRepository alarmRepository;

    private final ProfileRepository profileRepository;

    public void sendAlarm(
            Profile targetProfile,
            Profile requestProfile,
            AlarmType type,
            // MessageRoom, Tutoring, DateNotice
            Long contentId
    ) {
        Alarm alarm = Alarm.builder()
                .profileName(requestProfile.getName())
                .contentId(contentId)
                .alarmType(type)
                .alarmStatus(AlarmStatus.UNCHECK)
                .build();
        alarm.addProfile(targetProfile);
        alarmRepository.save(alarm);
    }

    public Page<Alarm> getAlarm(
            Long profileId,
            Pageable pageable
    ) {
        Profile findProfile = verifiedProfileById(profileId);
        return alarmRepository.findAllByProfile(findProfile, pageable);
    }

    public void updateAlarm(Long alarmId) {
        Alarm alarm = verifiedAlarmById(alarmId);
        alarm.setAlarmStatus(AlarmStatus.CHECK);
        alarmRepository.save(alarm);
    }

    public void deleteAlarm(Long alarmId) {
        Alarm alarm = verifiedAlarmById(alarmId);
        alarmRepository.delete(alarm);
    }

    public void updateAllAlarm(Long profileId) {
        Profile profile = verifiedProfileById(profileId);
        if (!profile.getAlarms().isEmpty()) {
            profile.getAlarms().stream()
                    .filter(a -> a.getAlarmStatus().equals(AlarmStatus.UNCHECK))
                    .forEach(a -> {
                        a.setAlarmStatus(AlarmStatus.CHECK);
                        alarmRepository.save(a);
                    });
        } else {
            throw new ServiceLogicException(ErrorCode.ALARM_CHECK_BAD_REQUEST);
        }
    }

    public void deleteAllAlarm(Long profileId) {
        Profile profile = verifiedProfileById(profileId);
        alarmRepository.deleteAllById(profile.getAlarms()
                .stream()
                .map(Alarm::getAlarmId)
                .collect(Collectors.toList())
        );
        profile.getAlarms().clear();

    }

    public boolean verifyAlarm(Long profileId) {
        Profile profile = verifiedProfileById(profileId);
        long count = profile.getAlarms().stream()
                .filter(a -> a.getAlarmStatus().equals(AlarmStatus.UNCHECK))
                .count();
        return count > 0;
    }


    private Alarm verifiedAlarmById(Long alarmId) {
        return alarmRepository.findById(alarmId)
                .orElseThrow(() -> new ServiceLogicException(ErrorCode.NOT_FOUND));
    }

    private Profile verifiedProfileById(Long profileId) {
        return profileRepository.findById(profileId)
                .orElseThrow(() -> new ServiceLogicException(ErrorCode.PROFILE_NOT_FOUND));
    }
}
