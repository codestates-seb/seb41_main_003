package com.mainproject.server.tutoring.dto;

import com.mainproject.server.dateNotice.dto.DateNoticeResponseDto;
import com.mainproject.server.tutoring.entity.Tutoring;
import lombok.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TutoringDto {

    private Long tutoringId;

    private String tutoringTitle;

    private String tutoringStatus;

    private String latestNoticeBody;

    private LocalDateTime createAt;

    private LocalDateTime updateAt;

    private Long  tutorId;

    private String  tutorName;

    private Long  tuteeId;

    private String  tuteeName;

    private Page<DateNoticeResponseDto> dateNotices;

    public TutoringDto(Tutoring tutoring, Pageable pageable) {
        this.tutoringId = tutoring.getTutoringId();
        this.tutoringTitle = tutoring.getTutoringTitle();
        this.tutoringStatus = tutoring.getTutoringStatus().name();
        this.latestNoticeBody = tutoring.getLatestNoticeBody();
        this.createAt = tutoring.getCreateAt();
        this.updateAt = tutoring.getUpdateAt();
        this.tutorId = tutoring.getTutor().getProfileId();
        this.tuteeId = tutoring.getTutee().getProfileId();
        this.tutorName = tutoring.getTutor().getName();
        this.tuteeName = tutoring.getTutee().getName();
        List<DateNoticeResponseDto> dtos = new ArrayList<>(tutoring.getDateNotices())
                .stream()
                .map(DateNoticeResponseDto::of)
                .collect(Collectors.toList());
        this.dateNotices = new PageImpl<>(dtos, pageable, dtos.size());
    }

    public static TutoringDto of(Tutoring tutoring, Pageable pageable) {
        return new TutoringDto(tutoring, pageable);
    }
}
