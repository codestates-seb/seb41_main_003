package com.mainproject.server.dateNotice.service;

import com.mainproject.server.constant.NoticeStatus;
import com.mainproject.server.dateNotice.entity.DateNotice;
import com.mainproject.server.dateNotice.repository.DateNoticeRepository;
import com.mainproject.server.dateNotice.repository.HomeworkRepository;
import com.mainproject.server.tutoring.entity.Tutoring;
import com.mainproject.server.tutoring.service.TutoringService;
import com.mainproject.server.utils.StubData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class DateNoticeServiceTest {

    @Mock
    private DateNoticeRepository dateNoticeRepository;

    @Mock
    private TutoringService tutoringService;

    @Mock
    private HomeworkRepository homeworkRepository;

    @InjectMocks
    private DateNoticeService dateNoticeService;

    @Test
    @DisplayName("DateNotice Update Check Notice 메소드 TEST")
    void givenDateNoticeAndTutoringWhenUpdateCheckNoticeThenReturnUpdateDateNotice() {
        // Given
        Long tutoringId = 1L;
        Tutoring tutoring = StubData.createTutoring(1L);
        DateNotice dateNotice1 = StubData.createDateNotice(1L);
        DateNotice dateNotice2 = StubData.createDateNotice(1L);
        // When
        DateNotice updateDateNotice1 = dateNoticeService.updateCheckNotice(dateNotice1, tutoring);
        dateNotice2.getNotice().setNoticeBody("");
        DateNotice updateDateNotice2 = dateNoticeService.updateCheckNotice(dateNotice2, tutoring);
        // Then
        assertThat(updateDateNotice1.getNoticeStatus()).isEqualTo(NoticeStatus.NOTICE);
        assertThat(updateDateNotice2.getNoticeStatus()).isEqualTo(NoticeStatus.NONE);
    }

}