package com.mainproject.server.tutoring.dto;

import com.mainproject.server.dateNotice.dto.DateNoticeResponseDto;
import com.mainproject.server.dateNotice.entity.DateNotice;
import com.mainproject.server.tutoring.entity.Tutoring;
import lombok.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.time.LocalDateTime;
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

    private Long latestNoticeId;

    private String latestNoticeBody;

    private LocalDateTime createAt;

    private LocalDateTime updateAt;

    private Long  tutorId;

    private String  tutorName;

    private Long  tuteeId;

    private String  tuteeName;

    private Page<DateNoticeResponseDto> dateNotices;

    public TutoringDto(Tutoring tutoring, Page<DateNotice> dateNoticePage) {
        this.tutoringId = tutoring.getTutoringId();
        this.tutoringTitle = tutoring.getTutoringTitle();
        this.tutoringStatus = tutoring.getTutoringStatus().name();
        this.latestNoticeId = tutoring.getLatestNoticeId();
        this.latestNoticeBody = tutoring.getLatestNoticeBody();
        this.createAt = tutoring.getCreateAt();
        this.updateAt = tutoring.getUpdateAt();
        this.tutorId = tutoring.getTutor().getProfileId();
        this.tuteeId = tutoring.getTutee().getProfileId();
        this.tutorName = tutoring.getTutor().getName();
        this.tuteeName = tutoring.getTutee().getName();
        if (!dateNoticePage.getContent().isEmpty()) {
            List<DateNoticeResponseDto> dtos = dateNoticePage.getContent()
                    .stream()
                    .map(DateNoticeResponseDto::of)
                    .collect(Collectors.toList());
            this.dateNotices = new PageImpl<>(dtos,
                    dateNotices.getPageable(),
                    dateNotices.getTotalElements());
        } else {
            this.dateNotices = new PageImpl<>(
                    List.of(),
                    dateNoticePage.getPageable(),
                    dateNoticePage.getTotalElements());
        }

    }

    public static TutoringDto of(Tutoring tutoring, Page<DateNotice> dateNoticePage) {
        return new TutoringDto(tutoring, dateNoticePage);
    }
}
