package com.mainproject.server.tutoring.mapper;

import com.mainproject.server.dateNotice.dto.DateNoticeSimpleResponseDto;
import com.mainproject.server.dateNotice.entity.DateNotice;
import com.mainproject.server.tutoring.dto.*;
import com.mainproject.server.tutoring.entity.Tutoring;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ValueMapping;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface TutoringMapper {

    @Mapping(source = "tutorId", target = "tutor.profileId")
    @Mapping(source = "tuteeId", target = "tutee.profileId")
    Tutoring tutoringPostDtoToTutoring(TutoringPostDto tutoringPostDto);

    Tutoring tutoringPatchDtoToTutoring(TutoringPatchDto tutoringPatchDto);

    @Mapping(source = "tutoringStatus", target = "tutoringStatus")
    @Mapping(source = "tutee.profileId", target = "tuteeId")
    @Mapping(source = "tutee.name", target = "tuteeName")
    @Mapping(source = "tutor.profileId", target = "tutorId")
    @Mapping(source = "tutor.name", target = "tutorName")
    @ValueMapping(source = "noticeStatus", target = "noticeStatus")
    TutoringResponseDto tutoringToTutoringResponseDto(Tutoring tutoring);

    @Mapping(source = "tutor.name", target = "tutorName")
    @Mapping(source = "tutee.name", target = "tuteeName")
    @ValueMapping(source = "tutoringStatus", target = "tutoringStatus")
    TutoringSimpleResponseDto tutoringToTutoringSimpleResponseDto(Tutoring tutoring);

    List<TutoringSimpleResponseDto> tutoringListToTutoringSimpleResponseDtoList(List<Tutoring> tutoringList);

}
