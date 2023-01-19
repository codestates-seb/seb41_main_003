package com.mainproject.server.utils;

import com.mainproject.server.constant.*;
import com.mainproject.server.dateNotice.entity.DateNotice;
import com.mainproject.server.dateNotice.entity.Homework;
import com.mainproject.server.dateNotice.entity.Notice;
import com.mainproject.server.dateNotice.entity.Schedule;
import com.mainproject.server.image.entity.ProfileImage;
import com.mainproject.server.profile.entity.Profile;
import com.mainproject.server.review.entity.Review;
import com.mainproject.server.subject.dto.SubjectDto;
import com.mainproject.server.subject.entity.Subject;
import com.mainproject.server.subject.entity.SubjectProfile;
import com.mainproject.server.tutoring.entity.Tutoring;
import com.mainproject.server.user.entity.User;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

public class StubData {

    public static User createUser() {
        return User.builder()
                .userId(1L)
                .email("test@test.com")
                .nickName("test")
                .password("1111!")
                .secondPassword("1234")
                .phoneNumber("010-0000-0000")
                .userStatus(UserStatus.TUTOR)
                .build();
    }
    public static Tutoring createTutoring(Long id) {
        Tutoring get = new Tutoring(
                id,
                "test",
                id,
                "test",
                TutoringStatus.UNCHECK,
                createProfile(id),
                createProfile(id),
                Set.of(createDateNotice(id), createDateNotice(id)),
                createReview(id)
        );
        get.setCreateAt(LocalDateTime.now());
        get.setUpdateAt(LocalDateTime.now());

        return get;
    }

    public static DateNotice createDateNotice(Long id) {
        Tutoring tutoring = new Tutoring(
                id,
                "test",
                id,
                "test",
                TutoringStatus.UNCHECK,
                createProfile(id),
                createProfile(id),
                Set.of(DateNotice.builder().dateNoticeId(id).build(), DateNotice.builder().dateNoticeId(id).build()),
                createReview(id)
        );

        DateNotice get = DateNotice.builder()
                .dateNoticeId(id)
                .dateNoticeTitle("test")
                .startTime("20230118")
                .endTime("20230119")
                .noticeStatus(NoticeStatus.NOTICE)
                .notice(createNotice(id))
                .schedule(createSchedule(id))
                .tutoring(tutoring)
                .homeworks(Set.of(createHomework(id), createHomework(id)))
                .build();
        get.setCreateAt(LocalDateTime.now());
        get.setUpdateAt(LocalDateTime.now());

        return get;
    }

    public static Notice createNotice(Long id) {
        Notice get = Notice.builder()
                .noticeId(id)
                .noticeBody("test")
                .build();

        get.setCreateAt(LocalDateTime.now());
        get.setUpdateAt(LocalDateTime.now());

        return get;
    }

    public static Schedule createSchedule(Long id) {
        Schedule get = Schedule.builder()
                .scheduleId(id)
                .scheduleBody("test")
                .build();

        get.setCreateAt(LocalDateTime.now());
        get.setUpdateAt(LocalDateTime.now());

        return get;
    }

    public static Homework createHomework(Long id) {
        Homework get = Homework.builder()
                .homeworkId(id)
                .homeworkBody("test")
                .homeworkStatus(HomeworkStatus.PROGRESS)
                .build();

        get.setCreateAt(LocalDateTime.now());
        get.setUpdateAt(LocalDateTime.now());

        return get;
    }



    public static Review createReview(Long id) {
        Review get = Review.builder()
                .reviewId(id)
                .professional(4)
                .readiness(5)
                .explanation(5)
                .punctuality(4)
                .reviewBody("test")
                .tutor(Profile.builder().name("test").build())
                .tutee(Profile.builder().name("test").build())
                .build();
        get.setCreateAt(LocalDateTime.now());
        get.setUpdateAt(LocalDateTime.now());
        return get;
    }

    public static Subject createSubject(Long id) {
        Subject get = Subject.builder()
                .subjectId(id)
                .subjectTitle("test")
                .subjectProfiles(new LinkedHashSet<>())
                .build();
        get.setCreateAt(LocalDateTime.now());
        get.setUpdateAt(LocalDateTime.now());
        return get;
    }

    public static SubjectDto createSubjectDto(Long id) {
        return new SubjectDto(id, "test", "test");
    }

    public static Profile createProfile(Long id) {
        Profile get = Profile.builder()
                .profileId(id)
                .name("test")
                .rate(3.5)
                .bio("test")
                .wantDate("test")
                .pay("test")
                .way("test")
                .profileStatus(ProfileStatus.TUTOR)
                .wantedStatus(WantedStatus.REQUEST)
                .gender("test")
                .school("test")
                .character("test")
                .preTutoring("test")
                .difference("test")
                .subjectString("test")
                .subjectProfiles(Set.of(createSubjectProfile(id), createSubjectProfile(id)))
                .reviews(Set.of(createReview(id), createReview(id)))
                .profileImage(createImage(id))
                .user(new User())
                .build();
        get.setCreateAt(LocalDateTime.now());
        get.setUpdateAt(LocalDateTime.now());
        return get;
    }

    public static ProfileImage createImage(Long id) {
        ProfileImage get = ProfileImage.builder()
                .profileImageId(id)
                .fileName("test")
                .url("test")
                .build();
        get.setCreateAt(LocalDateTime.now());
        get.setUpdateAt(LocalDateTime.now());
        return get;
    }

    public static SubjectProfile createSubjectProfile(Long id) {
        return SubjectProfile.builder()
                .profile(Profile.builder().profileId(id).build())
                .subject(createSubject(id))
                .content("test")
                .subjectProfileId(id)
                .build();
    }
}
