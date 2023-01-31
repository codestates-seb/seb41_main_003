package com.mainproject.server.tutoring.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TutoringSimpleResponseDto {

    private Long tutoringId;

    private String tutorName;

    private String tuteeName;

    private String tutoringTitle;

    private String tutoringStatus;

    private LocalDateTime createAt;

    private LocalDateTime updateAt;

    public TutoringSimpleResponseDto(TutoringQueryDto dto) {
        this.tutoringId = dto.getTutoringId();
        this.tutorName = dto.getTutorName();
        this.tuteeName = dto.getTuteeName();
        this.tutoringTitle = dto.getTutoringTitle();
        this.tutoringStatus = dto.getTutoringStatus().name();
        this.createAt = dto.getCreateAt();
        this.updateAt = dto.getUpdateAt();
    }

    public static TutoringSimpleResponseDto of(TutoringQueryDto dto) {
        return new TutoringSimpleResponseDto(dto);
    }
}
