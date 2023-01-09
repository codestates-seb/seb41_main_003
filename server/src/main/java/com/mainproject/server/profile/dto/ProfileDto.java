package com.mainproject.server.profile.dto;

import com.mainproject.server.subject.dto.SubjectDto;
import lombok.*;

import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class ProfileDto {

    private Long userId;

    private String name;

    private String bio;

    private String school;

    private String way;

    private List<SubjectDto> subjects;

    private String difference;

    private String gender;

    private String character;

    private String pay;

    private String wantDate;

    private String preTutoring;

}
