package com.mainproject.server.tutoring.dto;


import com.mainproject.server.constant.TutoringStatus;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TutoringQueryDto {

    private Long tutoringId;

    private String tutorName;

    private String tuteeName;

    private String tutoringTitle;

    private TutoringStatus tutoringStatus;

    private LocalDateTime createAt;

    private LocalDateTime updateAt;
}
