package com.mainproject.server.dateNotice.mapper;

import com.mainproject.server.constant.ErrorCode;
import com.mainproject.server.constant.HomeworkStatus;
import com.mainproject.server.dateNotice.dto.*;
import com.mainproject.server.dateNotice.entity.DateNotice;
import com.mainproject.server.dateNotice.entity.Homework;
import com.mainproject.server.exception.ServiceLogicException;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface DateNoticeMapper {

    @Mapping(source = "scheduleBody", target = "schedule.scheduleBody")
    @Mapping(source = "noticeBody", target = "notice.noticeBody")
    DateNotice dateNoticePostDtoToDateNotice(DateNoticePostDto dateNoticePostDto);

    @Mapping(source = "scheduleBody", target = "schedule.scheduleBody")
    @Mapping(source = "noticeBody", target = "notice.noticeBody")
    DateNotice dateNoticePatchDtoToDateNotice(DateNoticePatchDto dateNoticePatchDto);

    @Mapping(source = "notice.noticeId", target = "notice.noticeId")
    @Mapping(source = "notice.noticeBody", target = "notice.noticeBody")
    @Mapping(source = "schedule.scheduleId", target = "schedule.scheduleId")
    @Mapping(source = "schedule.scheduleBody", target = "schedule.scheduleBody")
    DateNoticeResponseDto dateNoticeToDateNoticeResponseDto(DateNotice dateNotice);

    List<DateNoticeResponseDto> dateNoticeSetToDateNoticeResponseDtoPage(Set<DateNotice> dateNoticeSet);

    List<DateNoticeSimpleResponseDto> dateNoticeSetToDateNoticeSimpleResponseDtoList(Set<DateNotice> dateNoticeSet);

    Set<Homework> HomeworkPostDtoListToHomeworkSet(List<HomeworkPostDto> homeworkPostDtoList);

    Set<Homework> HomeworkPatchDtoListToHomeworkSet(List<HomeworkPatchDto> homeworkPatchDtoList);



    List<HomeworkResponseDto> homeworkSetToHomeworkResponseDto(Set<Homework> homeworkSet);


    default Homework homeworkPostDtoToHomework(HomeworkPostDto homeworkPostDto) {
        if ( homeworkPostDto == null ) {
            return null;
        }

        Homework.HomeworkBuilder homework = Homework.builder();

        homework.homeworkBody( homeworkPostDto.getHomeworkBody() );
        if ( homeworkPostDto.getHomeworkStatus() != null ) {
            homework.homeworkStatus( Enum.valueOf( HomeworkStatus.class, homeworkPostDto.getHomeworkStatus() ) );
        } else {
            throw new ServiceLogicException(ErrorCode.HOMEWORK_STATUS_NOT_NULL);
        }

        return homework.build();
    }

    default Homework homeworkPatchDtoToHomework(HomeworkPatchDto homeworkPatchDto) {
        if ( homeworkPatchDto == null ) {
            return null;
        }

        Homework.HomeworkBuilder homework = Homework.builder();

        homework.homeworkBody( homeworkPatchDto.getHomeworkBody() );
        if ( homeworkPatchDto.getHomeworkStatus() != null ) {
            homework.homeworkStatus( Enum.valueOf( HomeworkStatus.class, homeworkPatchDto.getHomeworkStatus() ) );
        } else {
            throw new ServiceLogicException(ErrorCode.HOMEWORK_STATUS_NOT_NULL);
        }

        return homework.build();
    }



}
