package com.mainproject.server.profile.dto;

import com.mainproject.server.subject.dto.SubjectDto;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Getter
@Setter
public class ProfileDto {

    @NotBlank
    private String name;

    @NotBlank
    private String bio;

    @NotBlank
    private String school;

    @NotBlank
    private String way;

    @NotNull
    private List<SubjectDto> subjects;

    private String difference;

    @NotNull
    private String gender;

    private String character;

    @NotNull
    private String pay;

    @NotNull
    private String wantDate;

    private String preTutoring;

}
