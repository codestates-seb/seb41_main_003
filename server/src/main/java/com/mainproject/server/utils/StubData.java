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
import com.mainproject.server.profile.dto.ProfileResponseDto;
import com.mainproject.server.profile.dto.ProfileSimpleResponseDto;
import com.mainproject.server.review.dto.ReviewResponseDto;
import com.mainproject.server.subject.dto.SubjectProfileResponseDto;
import com.mainproject.server.subject.dto.SubjectResponseDto;
import com.mainproject.server.tutoring.dto.TutoringResponseDto;
import com.mainproject.server.tutoring.dto.TutoringSimpleResponseDto;
import com.mainproject.server.user.dto.UserResponseDto;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class StubData {

    public ReviewResponseDto createReviewResponse() {
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


    public UserResponseDto createUserResponse() {
        return UserResponseDto.builder()
                .userId(1L)
                .email("hosoo3513@gmail.com")
                .phoneNumber("010-1234-5678")
                .loginType(LoginType.SOCIAL.name())
                .userStatus(UserStatus.TUTOR.name())
                .createAt(LocalDateTime.now())
                .updateAt(LocalDateTime.now())
                .build();
    }

    public SubjectResponseDto createSubjectResponse() {
        return SubjectResponseDto.builder()
                .subjectId(1L)
                .subjectTitle("수학")
                .build();
    }

    public SubjectProfileResponseDto createSubjectProfileResponse() {
        return SubjectProfileResponseDto.builder()
                .subjectId(1L)
                .subjectTitle("수학")
                .content("수학공부를 합시다")
                .createAt(LocalDateTime.now())
                .updateAt(LocalDateTime.now())
                .build();
    }

    public ImageResponseDto createImageResponse() {
        return ImageResponseDto.builder()
                .profileImageId(1L)
                .url("https://www.google.com/url?sa=i&url=http%3A%2F%2Fm.blog.naver.com%2Fcjswodnajs%2F222138892587&psig=AOvVaw0Ef_d9Jqh-dQm9Q7RRDiIg&ust=1673341195393000&source=images&cd=vfe&ved=0CA8QjRxqFwoTCMjWyO2PuvwCFQAAAAAdAAAAABAE")
                .createAt(LocalDateTime.now())
                .updateAt(LocalDateTime.now())
                .build();
    }
    
    public DateNoticeResponseDto createDateNoticeResponse() {
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

    public HomeworkResponseDto createHomeworkResponse() {
        HomeworkResponseDto hwDto = new HomeworkResponseDto();
        hwDto.setHomeworkId(1L);
        hwDto.setHomeworkBody("TestBody");
        hwDto.setHomeworkStatus(HomeworkStatus.PROGRESS.name());
        return hwDto;
    }

    public TutoringResponseDto createTutoringResponse() {
        return TutoringResponseDto.builder()
                .tutoringId(1L)
                .tutoringStatus(TutoringStatus.TUTOR_WAITING.name())
                .createAt(LocalDateTime.now())
                .updateAt(LocalDateTime.now())
                .tutor(createProfileSimpleResponse())
                .tutee(createProfileSimpleResponse())
                .review(createReviewResponse())
                .dateNotices(List.of(
                        createDateNoticeResponse(),
                        createDateNoticeResponse(),
                        createDateNoticeResponse()

                )).build();

    }

    public TutoringSimpleResponseDto createTutoringSimpleResponse() {
        return TutoringSimpleResponseDto.builder()
                .tutoringId(1L)
                .tutorName("강호수")
                .tuteeName("김다은")
                .tutoringTitle("수학 뿌셔 과학 뿌셔")
                .tutoringStatus(TutoringStatus.TUTEE_WAITING.name())
                .createAt(LocalDateTime.now())
                .build();
    }

    public ProfileResponseDto createProfileResponse() {
        return ProfileResponseDto.builder()
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
                .reviews(List.of(
                        createReviewResponse(),
                        createReviewResponse()
                ))
                .profileImage(createImageResponse())
                .createAt(LocalDateTime.now())
                .updateAt(LocalDateTime.now())
                .build();
    }

    public ProfileSimpleResponseDto createProfileSimpleResponse() {
        return ProfileSimpleResponseDto.builder()
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

    public MessageRoomSimpleResponseDto createMessageRoomSimpleResponse() {
        return MessageRoomSimpleResponseDto.builder()
                .messageRoomId(1L)
                .messageStatus(MessageStatus.UNCHECK.name())
                .lastMessage("자니?")
                .targetName("튯허")
                .createAt(LocalDateTime.now())
                .build();
    }
    public MessageResponseDto createMessageResponse() {
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

    public MessageRoomResponseDto createMessageRoomResponse() {
        return MessageRoomResponseDto.builder()
                .messageRoomId(1L)
                .targetName("너에게")
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
