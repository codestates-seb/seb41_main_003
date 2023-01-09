package com.mainproject.server.subject.dto;


import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SubjectResponseDto {

    private Long subjectId;

    private String subjectTitle;

}
