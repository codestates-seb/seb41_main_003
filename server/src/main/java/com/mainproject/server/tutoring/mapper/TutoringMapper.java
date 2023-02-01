package com.mainproject.server.tutoring.mapper;

import com.mainproject.server.tutoring.dto.*;
import com.mainproject.server.tutoring.entity.Tutoring;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ValueMapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TutoringMapper {

    @Mapping(source = "tutorId", target = "tutor.profileId")
    @Mapping(source = "tuteeId", target = "tutee.profileId")
    Tutoring tutoringPostDtoToTutoring(TutoringPostDto tutoringPostDto);

    Tutoring tutoringPatchDtoToTutoring(TutoringPatchDto tutoringPatchDto);

    @Mapping(source = "tutor.name", target = "tutorName")
    @Mapping(source = "tutee.name", target = "tuteeName")
    @ValueMapping(source = "tutoringStatus", target = "tutoringStatus")
    TutoringSimpleResponseDto tutoringToTutoringSimpleResponseDto(Tutoring tutoring);

    TutoringSimpleResponseDto dtoToSimpleResponseDto(TutoringDto dto);

    List<TutoringSimpleResponseDto> tutoringListToTutoringSimpleResponseDtoList(List<Tutoring> tutoringList);

}
