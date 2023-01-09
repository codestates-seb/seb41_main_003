package com.mainproject.server.subject.dto;


import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SubjectProfileResponseDto {

    private Long subjectId;

    private String subjectTitle;

    private String content;

    private LocalDateTime createAt;

    private LocalDateTime updateAt;
}
