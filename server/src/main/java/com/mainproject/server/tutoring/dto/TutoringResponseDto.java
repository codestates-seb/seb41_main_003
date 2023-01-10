package com.mainproject.server.tutoring.dto;


import com.mainproject.server.dateNotice.dto.DateNoticeSimpleResponseDto;
import com.mainproject.server.subject.dto.SubjectResponseDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class TutoringResponseDto {

    private Long tutoringId;

    private String tutoringStatus;

    private Long tuteeId;

    private String tuteeName;

    private List<SubjectResponseDto> tuteeSubjects;

    private Long tutorId;

    private String tutorName;

    private List<SubjectResponseDto> tutorSubjects;

    private LocalDateTime createAt;

    private LocalDateTime updateAt;

    private List<DateNoticeSimpleResponseDto> dateNotices;

    public TutoringResponseDto(TutoringDto dto) {
        this.tutoringId = dto.getTutoringId();
        this.tutoringStatus = dto.getTutoringStatus();
        this.tuteeId = dto.getTutee().getProfileId();
        this.tuteeName = dto.getTutee().getName();
        this.tuteeSubjects = dto.getTutee().getSubjects();
        this.tutorId = dto.getTutor().getProfileId();
        this.tutorName = dto.getTutor().getName();
        this.tutorSubjects = dto.getTutor().getSubjects();
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
