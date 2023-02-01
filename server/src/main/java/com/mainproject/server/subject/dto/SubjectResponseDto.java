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

    public SubjectResponseDto(String title) {
        this.subjectId = null;
        this.subjectTitle = title;
    }

    public static SubjectResponseDto of(String title) {
        return new SubjectResponseDto(title);
    }

}
