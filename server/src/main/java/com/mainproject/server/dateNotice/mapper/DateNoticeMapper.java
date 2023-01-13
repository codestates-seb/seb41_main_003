package com.mainproject.server.dateNotice.mapper;

import com.mainproject.server.dateNotice.dto.*;
import com.mainproject.server.dateNotice.entity.DateNotice;
import com.mainproject.server.dateNotice.entity.Homework;
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



}
