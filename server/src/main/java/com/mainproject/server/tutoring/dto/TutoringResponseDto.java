package com.mainproject.server.tutoring.dto;


import com.mainproject.server.dateNotice.dto.DateNoticeSimpleResponseDto;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class TutoringResponseDto {

    private Long tutoringId;

    private String tutoringTitle;

    private String tutoringStatus;

    private Long latestNoticeId;

    private String latestNoticeBody;

    private Long tuteeId;

    private String tuteeName;

    private Long tutorId;

    private String tutorName;

    private LocalDateTime createAt;

    private LocalDateTime updateAt;

    private List<DateNoticeSimpleResponseDto> dateNotices;

    public TutoringResponseDto(TutoringDto dto) {
        this.tutoringId = dto.getTutoringId();
        this.tutoringTitle = dto.getTutoringTitle();
        this.tutoringStatus = dto.getTutoringStatus();
        this.latestNoticeId = dto.getLatestNoticeId();
        this.latestNoticeBody = dto.getLatestNoticeBody();
        this.tuteeId = dto.getTuteeId();
        this.tuteeName = dto.getTuteeName();
        this.tutorId = dto.getTutorId();
        this.tutorName = dto.getTutorName();
        this.createAt = dto.getCreateAt();
        this.updateAt = dto.getUpdateAt();
        this.dateNotices = dto.getDateNotices().getContent()
                .stream()
                .map(DateNoticeSimpleResponseDto::of)
                .collect(Collectors.toList());
    }

    public static TutoringResponseDto of(TutoringDto dto) {
        return new TutoringResponseDto(dto);
    }
}
