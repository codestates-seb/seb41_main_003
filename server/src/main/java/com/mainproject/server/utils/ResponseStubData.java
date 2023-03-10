package com.mainproject.server.utils;


import com.mainproject.server.constant.*;
import com.mainproject.server.dateNotice.dto.DateNoticeResponseDto;
import com.mainproject.server.dateNotice.dto.HomeworkResponseDto;
import com.mainproject.server.dateNotice.dto.NoticeResponseDto;
import com.mainproject.server.dateNotice.dto.ScheduleResponseDto;
import com.mainproject.server.image.dto.ImageResponseDto;
import com.mainproject.server.image.entity.ProfileImage;
import com.mainproject.server.message.dto.MessageResponseDto;
import com.mainproject.server.message.dto.MessageRoomQueryDto;
import com.mainproject.server.message.dto.MessageRoomResponseDto;
import com.mainproject.server.message.dto.MessageRoomSimpleResponseDto;
import com.mainproject.server.profile.dto.ProfileListResponseDto;
import com.mainproject.server.profile.dto.ProfilePageDto;
import com.mainproject.server.profile.dto.ProfileSimpleResponseDto;
import com.mainproject.server.profile.entity.Profile;
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
                .nickName("?????????")
                .loginType(LoginType.SOCIAL.name())
                .userStatus(UserStatus.TUTOR.name())
                .createAt(LocalDateTime.now())
                .updateAt(LocalDateTime.now())
                .build();
    }

    public static SubjectResponseDto createSubjectResponse() {
        return SubjectResponseDto.builder()
                .subjectId(1L)
                .subjectTitle("??????")
                .build();
    }

    public static SubjectProfileResponseDto createSubjectProfileResponse() {
        return SubjectProfileResponseDto.builder()
                .subjectId(1L)
                .subjectTitle("??????")
                .content("??????????????? ?????????")
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
                .noticeStatus(NoticeStatus.NOTICE.name())
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
        Page page = new PageImpl(list, PageRequest.of(0, 5), list.size());
        return TutoringDto.builder()
                .tutoringId(1L)
                .tutoringTitle("????????? ???????????????! ??????????????????!")
                .tutoringStatus(TutoringStatus.TUTOR_WAITING.name())
                .latestNoticeId(1L)
                .latestNoticeBody("??????")
                .createAt(LocalDateTime.now())
                .updateAt(LocalDateTime.now())
                .tutorId(createProfileListResponse().getProfileId())
                .tuteeId(createProfileListResponse().getProfileId())
                .tutorName(createProfileListResponse().getName())
                .tuteeName(createProfileListResponse().getName())
                .reviewId(createReviewResponse().getReviewId())
                .dateNotices(page).build();
    }

    public static TutoringSimpleResponseDto createTutoringSimpleResponse() {
        return TutoringSimpleResponseDto.builder()
                .tutoringId(1L)
                .tutorName("?????????")
                .tuteeName("?????????")
                .tutoringTitle("?????? ?????? ?????? ??????")
                .tutoringStatus(TutoringStatus.TUTEE_WAITING.name())
                .createAt(LocalDateTime.now())
                .updateAt(LocalDateTime.now())
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
                .name("?????????")
                .rate(4.9)
                .bio("?????? ??? ???????????????.")
                .school("?????????")
                .wantedStatus(WantedStatus.REQUEST.name())
                .way("?????? ??????????")
                .subjects(List.of(
                        createSubjectProfileResponse(),
                        createSubjectProfileResponse()
                ))
                .difference("??? ???????????????...")
                .gender("??????")
                .pay("4??????")
                .wantDate("????????? ?????????")
                .preTutoring("??????????????????")
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
                .name("?????????")
                .rate(4.9)
                .bio("?????? ??? ???????????????.")
                .school("?????????")
                .wantedStatus(WantedStatus.REQUEST.name())
                .way("?????? ??????????")
                .subjects(new ArrayList<>())
                .difference("??? ???????????????...")
                .gender("??????")
                .pay("4??????")
                .wantDate("????????? ?????????")
                .preTutoring("??????????????????")
                .reviews(page)
                .profileImage(createImageResponse())
                .createAt(LocalDateTime.now())
                .updateAt(LocalDateTime.now())
                .build();
    }

    public static ProfileListResponseDto createProfileListResponse() {
        return ProfileListResponseDto.builder()
                .profileId(1L)
                .name("?????????")
                .rate(4.5)
                .subjects(List.of(
                        createSubjectResponse(),
                        createSubjectResponse(),
                        createSubjectResponse()))
                .school("MIT")
                .bio("????????? ?????? ?????? ??????")
                .profileImage(createImageResponse())
                .createAt(LocalDateTime.now())
                .updateAt(LocalDateTime.now())
                .build();
    }

    public static ProfileSimpleResponseDto createProfileSimpleResponse() {
        return ProfileSimpleResponseDto.builder()
                .profileId(1L)
                .name("?????????")
                .url("https://www.google.com/url?sa=i&url=http%3A%2F%2Fm.blog.naver.com%2Fcjswodnajs%2F222138892587&psig=AOvVaw0Ef_d9Jqh-dQm9Q7RRDiIg&ust=1673341195393000&source=images&cd=vfe&ved=0CA8QjRxqFwoTCMjWyO2PuvwCFQAAAAAdAAAAABAE")
                .school("????????? ????????? 17??????")
                .build();
    }

    public static MessageRoomSimpleResponseDto createMessageRoomSimpleResponse() {
        return MessageRoomSimpleResponseDto.builder()
                .messageRoomId(1L)
                .messageStatus(MessageStatus.UNCHECK.name())
                .lastMessage("???????")
                .targetName("??????")
                .createAt(LocalDateTime.now())
                .build();
    }
    public static MessageResponseDto createMessageResponse() {
        return MessageResponseDto.builder()
                .messageRoomId(1L)
                .messageId(1L)
                .senderId(1L)
                .senderName("?????????")
                .receiverId(1L)
                .receiverName("?????????")
                .messageContent("???????????? ???????????? ????????? ?????????")
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
                .tutorName("?????????")
                .tuteeId(1L)
                .tuteeName("?????????")
                .tutoringId(1L)
                .build();
    }

    public static MessageRoomQueryDto createMessageRoomQueryDto() {
        Profile pro = Profile.builder()
                .profileId(1L)
                .profileStatus(ProfileStatus.TUTEE)
                .name("test")
                .profileImage(ProfileImage.builder().url("testUrl").build())
                .build();
        return MessageRoomQueryDto.builder()
                .messageRoomId(1L)
                .messageStatus(MessageStatus.CHECK)
                .tutoringId(1L)
                .tutor(pro)
                .tutee(pro)
                .tuteeProfileImageUrl("testUrl")
                .tutorProfileImageUrl("testUrl")
                .lastMessage("test")
                .lastSenderId(1L)
                .currentProfileStatus(ProfileStatus.TUTEE)
                .createAt(LocalDateTime.now())
                .updateAt(LocalDateTime.now())
                .build();
    }


}
