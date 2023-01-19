package com.mainproject.server.utils;


import com.mainproject.server.constant.*;
import com.mainproject.server.dateNotice.dto.DateNoticeResponseDto;
import com.mainproject.server.dateNotice.dto.HomeworkResponseDto;
import com.mainproject.server.dateNotice.dto.NoticeResponseDto;
import com.mainproject.server.dateNotice.dto.ScheduleResponseDto;
import com.mainproject.server.image.dto.ImageResponseDto;
import com.mainproject.server.message.dto.MessageResponseDto;
import com.mainproject.server.message.dto.MessageRoomResponseDto;
import com.mainproject.server.message.dto.MessageRoomSimpleResponseDto;
import com.mainproject.server.profile.dto.ProfileListResponseDto;
import com.mainproject.server.profile.dto.ProfilePageDto;
import com.mainproject.server.profile.dto.ProfileSimpleResponseDto;
import com.mainproject.server.review.dto.ReviewResponseDto;
import com.mainproject.server.subject.dto.SubjectProfileResponseDto;
import com.mainproject.server.subject.dto.SubjectResponseDto;
import com.mainproject.server.tutoring.dto.TutoringDto;
import com.mainproject.server.tutoring.dto.TutoringSimpleResponseDto;
import com.mainproject.server.user.dto.UserResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ResponseStubData {

    public static ReviewResponseDto createReviewResponse() {
        return ReviewResponseDto.builder()
                .reviewId(1L)
                .professional(4)
                .readiness(4)
                .explanation(5)
                .punctuality(5)
                .reviewBody("TestBody")
                .tuteeName("testTutee")
                .createAt(LocalDateTime.now())
                .updateAt(LocalDateTime.now())
                .build();
    }


    public static UserResponseDto createUserResponse() {
        return UserResponseDto.builder()
                .userId(1L)
                .email("test@test.com")
                .phoneNumber("01012345678")
                .nickName("수포자")
                .loginType(LoginType.SOCIAL.name())
                .userStatus(UserStatus.TUTOR.name())
                .createAt(LocalDateTime.now())
                .updateAt(LocalDateTime.now())
                .build();
    }

    public static SubjectResponseDto createSubjectResponse() {
        return SubjectResponseDto.builder()
                .subjectId(1L)
                .subjectTitle("수학")
                .build();
    }

    public static SubjectProfileResponseDto createSubjectProfileResponse() {
        return SubjectProfileResponseDto.builder()
                .subjectId(1L)
                .subjectTitle("수학")
                .content("수학공부를 합시다")
                .createAt(LocalDateTime.now())
                .updateAt(LocalDateTime.now())
                .build();
    }

    public static ImageResponseDto createImageResponse() {
        return ImageResponseDto.builder()
                .profileImageId(1L)
                .url("https://www.google.com/url?sa=i&url=http%3A%2F%2Fm.blog.naver.com%2Fcjswodnajs%2F222138892587&psig=AOvVaw0Ef_d9Jqh-dQm9Q7RRDiIg&ust=1673341195393000&source=images&cd=vfe&ved=0CA8QjRxqFwoTCMjWyO2PuvwCFQAAAAAdAAAAABAE")
                .createAt(LocalDateTime.now())
                .updateAt(LocalDateTime.now())
                .build();
    }
    
    public static DateNoticeResponseDto createDateNoticeResponse() {
        ScheduleResponseDto scDto = new ScheduleResponseDto();
        scDto.setScheduleId(1L);
        scDto.setScheduleBody("TestBody");
        NoticeResponseDto ntDto = new NoticeResponseDto();
        ntDto.setNoticeId(1L);
        ntDto.setNoticeBody("TestBody");
        return DateNoticeResponseDto.builder()
                .dateNoticeId(1L)
                .dateNoticeTitle("TestTitle")
                .startTime(LocalDateTime.now().toString())
                .endTime(LocalDateTime.now().toString())
                .schedule(scDto)
                .notice(ntDto)
                .homeworks(List.of(
                        createHomeworkResponse(),
                        createHomeworkResponse(),
                        createHomeworkResponse()))
                .build();
    }

    public static HomeworkResponseDto createHomeworkResponse() {
        HomeworkResponseDto hwDto = new HomeworkResponseDto();
        hwDto.setHomeworkId(1L);
        hwDto.setHomeworkBody("TestBody");
        hwDto.setHomeworkStatus(HomeworkStatus.PROGRESS.name());
        return hwDto;
    }

    public static TutoringDto createTutoringDto() {
        List<DateNoticeResponseDto> list = List.of(
                createDateNoticeResponse(),
                createDateNoticeResponse(),
                createDateNoticeResponse()

        );
        Page page = new PageImpl(list, PageRequest.of(0, 10), list.size());
        return TutoringDto.builder()
                .tutoringId(1L)
                .tutoringTitle("열심히 가르칩니다! 강호수입니다!")
                .tutoringStatus(TutoringStatus.TUTOR_WAITING.name())
                .createAt(LocalDateTime.now())
                .updateAt(LocalDateTime.now())
                .tutorId(createProfileListResponse().getProfileId())
                .tuteeId(createProfileListResponse().getProfileId())
                .tutorName(createProfileListResponse().getName())
                .tuteeName(createProfileListResponse().getName())
                .dateNotices(page).build();
    }

    public static TutoringSimpleResponseDto createTutoringSimpleResponse() {
        return TutoringSimpleResponseDto.builder()
                .tutoringId(1L)
                .tutorName("강호수")
                .tuteeName("김다은")
                .tutoringTitle("수학 뿌셔 과학 뿌셔")
                .tutoringStatus(TutoringStatus.TUTEE_WAITING.name())
                .createAt(LocalDateTime.now())
                .build();
    }

    public static ProfilePageDto createProfileResponse() {
        List<ReviewResponseDto> list = List.of(
                createReviewResponse(),
                createReviewResponse()
        );
        Page page = new PageImpl(list, PageRequest.of(0, 10), list.size());
        return ProfilePageDto.builder()
                .profileId(1L)
                .name("강호수")
                .rate(4.9)
                .bio("진짜 잘 가르칩니다.")
                .school("낙성대")
                .wantedStatus(WantedStatus.REQUEST.name())
                .way("같이 술한잔?")
                .subjects(List.of(
                        createSubjectProfileResponse(),
                        createSubjectProfileResponse()
                ))
                .difference("제 차별성은요...")
                .gender("남자")
                .pay("4딸라")
                .wantDate("주말이 좋아요")
                .preTutoring("불가능합니다")
                .reviews(page)
                .profileImage(createImageResponse())
                .createAt(LocalDateTime.now())
                .updateAt(LocalDateTime.now())
                .build();
    }

    public static ProfilePageDto createEmptyProfileResponse() {
        Page page = new PageImpl(new ArrayList<>(), PageRequest.of(0, 10), 10L);
        return ProfilePageDto.builder()
                .profileId(1L)
                .name("강호수")
                .rate(4.9)
                .bio("진짜 잘 가르칩니다.")
                .school("낙성대")
                .wantedStatus(WantedStatus.REQUEST.name())
                .way("같이 술한잔?")
                .subjects(new ArrayList<>())
                .difference("제 차별성은요...")
                .gender("남자")
                .pay("4딸라")
                .wantDate("주말이 좋아요")
                .preTutoring("불가능합니다")
                .reviews(page)
                .profileImage(createImageResponse())
                .createAt(LocalDateTime.now())
                .updateAt(LocalDateTime.now())
                .build();
    }

    public static ProfileListResponseDto createProfileListResponse() {
        return ProfileListResponseDto.builder()
                .profileId(1L)
                .name("어때요")
                .rate(4.8)
                .subjects(List.of(
                        createSubjectResponse(),
                        createSubjectResponse(),
                        createSubjectResponse()))
                .school("MIT")
                .bio("대치동 원탑 수학 머신")
                .profileImage(createImageResponse())
                .createAt(LocalDateTime.now())
                .updateAt(LocalDateTime.now())
                .build();
    }

    public static ProfileSimpleResponseDto createProfileSimpleResponse() {
        return ProfileSimpleResponseDto.builder()
                .profileId(1L)
                .name("어때요")
                .url("https://www.google.com/url?sa=i&url=http%3A%2F%2Fm.blog.naver.com%2Fcjswodnajs%2F222138892587&psig=AOvVaw0Ef_d9Jqh-dQm9Q7RRDiIg&ust=1673341195393000&source=images&cd=vfe&ved=0CA8QjRxqFwoTCMjWyO2PuvwCFQAAAAAdAAAAABAE")
                .school("서운대 철학과 17학번")
                .build();
    }

    public static MessageRoomSimpleResponseDto createMessageRoomSimpleResponse() {
        return MessageRoomSimpleResponseDto.builder()
                .messageRoomId(1L)
                .messageStatus(MessageStatus.UNCHECK.name())
                .lastMessage("자니?")
                .targetName("튯허")
                .createAt(LocalDateTime.now())
                .build();
    }
    public static MessageResponseDto createMessageResponse() {
        return MessageResponseDto.builder()
                .messageId(1L)
                .senderId(1L)
                .senderName("홍길동")
                .receiverId(1L)
                .receiverName("김코딩")
                .messageContent("아버지를 아버지라 부르지 못하고")
                .createAt(LocalDateTime.now())
                .build();
    }

    public static MessageRoomResponseDto createMessageRoomResponse() {
        return MessageRoomResponseDto.builder()
                .messageRoomId(1L)
                .messages(List.of(
                        createMessageResponse(),
                        createMessageResponse(),
                        createMessageResponse()
                ))
                .createAt(LocalDateTime.now())
                .tutorId(1L)
                .tutorName("너에게")
                .tuteeId(1L)
                .tuteeName("나에게")
                .build();
    }




}
