package com.mainproject.server.tutoring.service;

import com.mainproject.server.alarm.service.AlarmService;
import com.mainproject.server.constant.AlarmType;
import com.mainproject.server.constant.ErrorCode;
import com.mainproject.server.constant.ProfileStatus;
import com.mainproject.server.constant.TutoringStatus;
import com.mainproject.server.dateNotice.entity.DateNotice;
import com.mainproject.server.dateNotice.repository.DateNoticeRepository;
import com.mainproject.server.exception.ServiceLogicException;
import com.mainproject.server.message.service.MessageService;
import com.mainproject.server.profile.entity.Profile;
import com.mainproject.server.profile.service.ProfileService;
import com.mainproject.server.tutoring.dto.TutoringDto;
import com.mainproject.server.tutoring.dto.TutoringQueryDto;
import com.mainproject.server.tutoring.dto.TutoringSimpleResponseDto;
import com.mainproject.server.tutoring.entity.Tutoring;
import com.mainproject.server.tutoring.repository.TutoringRepository;
import com.mainproject.server.utils.StubData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class TutoringServiceTest {

    @Mock
    private TutoringRepository tutoringRepository;

    @Mock
    private ProfileService profileService;

    @Mock
    private MessageService messageService;

    @Mock
    private AlarmService alarmService;

    @Mock
    private DateNoticeRepository dateNoticeRepository;

    @InjectMocks
    private TutoringService tutoringService;

    @Test
    @DisplayName("특정 프로필 과외 리스트 - 동작 TEST")
    void getAllTutoring() {
        //given
        Map<String, String> param = new HashMap<>();
        param.put("get", "PROGRESS");
        Pageable page = PageRequest.of(1,10);
        Long profileId = 1L;
        TutoringQueryDto tutoring1 = new TutoringQueryDto();
        tutoring1.setTutoringStatus(TutoringStatus.FINISH);
        List list1 = List.of(tutoring1, tutoring1);
        Page<TutoringQueryDto> result1 = new PageImpl<>(list1, page, list1.size());
        given(tutoringRepository.findQueryTutoring(
                anyLong(),
                any(TutoringStatus.class),
                any(Pageable.class)
        )).willReturn(result1);
        //when
        Page<TutoringSimpleResponseDto> testResult =
                tutoringService.getAllTutoring(param, profileId, page);
        //then
        assertThat(testResult.getContent().get(0).getTutoringStatus())
                .isEqualTo(TutoringStatus.FINISH.name());

    }

    @Test
    @DisplayName("특정 프로필 과외 리스트 - Parameter Input 예외 TEST")
    void getAllTutoringThrowServiceLogicException() {
        // Given
        Long profileId = 1L;
        Pageable page = PageRequest.of(1,10);
        Map<String, String> param = new HashMap<>();
        // When
        Throwable throwable = catchThrowable(
                () -> tutoringService.getAllTutoring(param, profileId, page)
        );
        // Then
        assertThat(throwable).isInstanceOf(ServiceLogicException.class)
                .hasMessageContaining(ErrorCode.NOT_NULL_WRONG_PROFILE_STATUS_PROPERTY.getMessage());
    }

    @Test
    @DisplayName("특정 과외 조회 TEST - UNCHECK 상태 Tutoring -> PROGRESS Tutoring")
    void getTutoring() {

        //given
        Long tutoringId = 1L;
        Long profileId = 1L;
        Pageable page = PageRequest.of(0, 5);

        Tutoring tutoring = StubData.createTutoring(tutoringId);
        Page<DateNotice> pageList = new PageImpl<>(
                new ArrayList<>(tutoring.getDateNotices()),
                page,
                10
        );
        given(dateNoticeRepository.findAllByTutoring(any(Tutoring.class), any(Pageable.class)))
                .willReturn(pageList);
        given(tutoringRepository.findTutoringById(anyLong())).
                willReturn(tutoring);
        given(tutoringRepository.save(any(Tutoring.class)))
                .willReturn(tutoring);
        //when
        TutoringDto dto = tutoringService.getTutoring(tutoringId, profileId, page);

        //then
        assertThat(dto.getTutoringStatus()).isEqualTo(TutoringStatus.PROGRESS.name());

    }

    @Test
    @DisplayName("튜터링 상태 PROGRESS로 전환 TEST")
    void setTutoringStatusProgress() {

        //given
        Long tutoringId = 1L;
        Long profileId = 1L;
        Pageable page = PageRequest.of(0, 5);

        Tutoring tutoring = StubData.createTutoring(tutoringId);
        Page<DateNotice> pageList = new PageImpl<>(
                new ArrayList<>(tutoring.getDateNotices()),
                page,
                10
        );
        given(dateNoticeRepository.findAllByTutoring(any(Tutoring.class), any(Pageable.class)))
                .willReturn(pageList);
        given(tutoringRepository.findTutoringById(anyLong())).
                willReturn(tutoring);
        given(tutoringRepository.save(any(Tutoring.class)))
                .willReturn(tutoring);
        doNothing().when(alarmService).sendAlarm(any(Profile.class),any(Profile.class),any(AlarmType.class),anyLong());
        //when
        TutoringDto dto = tutoringService.setTutoringStatusProgress(tutoringId, profileId, page);

        //then
        assertThat(dto.getTutoringStatus()).isEqualTo(TutoringStatus.PROGRESS.name());
    }

    @Test
    @DisplayName("튜터링 상태 PROGRESS로 전환 TEST - ACCESS DENIED")
    void setTutoringStatusProgressError() {

        //given
        Long tutoringId = 1L;
        Long profileId = 1L;
        Pageable page = PageRequest.of(0, 5);

        Tutoring tutoring = StubData.createTutoring(tutoringId);
        tutoring.setTutoringStatus(TutoringStatus.FINISH);

        given(tutoringRepository.findTutoringById(anyLong())).
                willReturn(tutoring);

        //when
        Throwable throwable = catchThrowable(
                () -> tutoringService.setTutoringStatusProgress(tutoringId, profileId, page)
        );

        //then
        assertThat(throwable)
                .isInstanceOf(ServiceLogicException.class)
                .hasMessageContaining(ErrorCode.TUTORING_STATUS_BAD_REQUEST.getMessage());
    }



    @Test
    @DisplayName("튜터링 조회 TEST - TUTORING NOT FOUND EXCEPTION")
    void verifiedTutoring(){

        //given
        Long tutoringId = 1L;
        given(tutoringRepository.findTutoringById(anyLong())).
                willThrow(new ServiceLogicException(ErrorCode.NOT_FOUND));

        //when
        Throwable throwable = catchThrowable(() -> tutoringService.verifiedTutoring(tutoringId));

        //then
        assertThat(throwable)
                .isInstanceOf(ServiceLogicException.class)
                .hasMessageContaining(ErrorCode.NOT_FOUND.getMessage());
    }

    @Test
    @DisplayName("튜터링 Status 조회 TEST - tutee가 매칭요청 했을 때")
    void getTuteeTutoringStatus() {

        //given
        Profile tuteeProfile = Profile.builder()
                .profileStatus(ProfileStatus.TUTEE)
                .build();
        Long profileId = 1L;

        given(profileService.verifiedProfileById(anyLong())).
                willReturn(tuteeProfile);

        //when
        TutoringStatus tutoringStatus = tutoringService.getTutoringStatus(profileId);

        //then
        assertThat(tutoringStatus).isEqualTo(TutoringStatus.TUTOR_WAITING);
    }

    @Test
    @DisplayName("튜터링 Status 조회 TEST - tutor가 매칭요청 했을 때")
    void getTutorTutoringStatus() {

        //given
        Profile tutorProfile = Profile.builder()
                .profileStatus(ProfileStatus.TUTOR)
                .build();
        Long profileId = 1L;

        given(profileService.verifiedProfileById(anyLong())).
                willReturn(tutorProfile);

        //when
        TutoringStatus tutoringStatus = tutoringService.getTutoringStatus(profileId);

        //then
        assertThat(tutoringStatus).isEqualTo(TutoringStatus.TUTEE_WAITING);
    }


}