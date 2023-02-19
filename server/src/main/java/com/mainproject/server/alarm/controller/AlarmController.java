package com.mainproject.server.alarm.controller;


import com.mainproject.server.alarm.dto.AlarmDto;
import com.mainproject.server.alarm.entity.Alarm;
import com.mainproject.server.alarm.service.AlarmService;
import com.mainproject.server.dto.PageResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/alarm")
public class AlarmController {

    private final AlarmService alarmService;


    @GetMapping("/{profileId}")
    public ResponseEntity getVerifyAlarm(
            @PathVariable Long profileId
    ) {
        boolean comp = alarmService.verifyAlarm(profileId);
        return new ResponseEntity(
                ResponseEntity.ok(Map.of("alarmCheck",comp)),
                HttpStatus.OK
        );
    }

    @GetMapping("/all/{profileId}")
    public ResponseEntity getAlarm(
            @PathVariable Long profileId,
            @PageableDefault(page = 0, size = 12, sort = "alarmId", direction = Sort.Direction.DESC)
            Pageable pageable
    ) {
        Page<Alarm> alarm = alarmService.getAlarm(profileId, pageable);
        List<AlarmDto> dtoList = alarm.getContent()
                .stream()
                .map(AlarmDto::of)
                .collect(Collectors.toList());
        return new ResponseEntity(
                PageResponseDto.of(dtoList, alarm),
                HttpStatus.OK
        );
    }

    @PatchMapping("/all/{profileId}")
    public ResponseEntity patchAllAlarm(
            @PathVariable Long profileId
    ) {
        alarmService.updateAllAlarm(profileId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/all/{profileId}")
    public ResponseEntity deleteAllAlarm(
            @PathVariable Long profileId
    ) {
        alarmService.deleteAllAlarm(profileId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/detail/{alarmId}")
    public ResponseEntity patchAlarm(
            @PathVariable Long alarmId
    ) {
        alarmService.updateAlarm(alarmId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/detail/{alarmId}")
    public ResponseEntity deleteAlarm(
            @PathVariable Long alarmId
    ) {
        alarmService.deleteAlarm(alarmId);
        return ResponseEntity.noContent().build();
    }
}
