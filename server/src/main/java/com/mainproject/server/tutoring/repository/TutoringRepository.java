package com.mainproject.server.tutoring.repository;

import com.mainproject.server.constant.TutoringStatus;
import com.mainproject.server.tutoring.entity.Tutoring;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TutoringRepository extends JpaRepository<Tutoring, Long> {
    Page<Tutoring> findAllByTutorProfileIdAndTutoringStatusOrTutorProfileIdAndTutoringStatusOrTutorProfileIdAndTutoringStatus(
            Long tutorProfileIdOne,
            TutoringStatus tutoringStatusOne,
            Long tutorProfileIdTwo,
            TutoringStatus tutoringStatusTwo,
            Long tutorProfileIdThree,
            TutoringStatus tutoringStatusThree,
            Pageable pageable
    );

    Page<Tutoring> findAllByTuteeProfileIdAndTutoringStatusOrTuteeProfileIdAndTutoringStatusOrTuteeProfileIdAndTutoringStatus(
            Long tuteeProfileIdOne,
            TutoringStatus tutoringStatusOne,
            Long tuteeProfileIdTwo,
            TutoringStatus tutoringStatusTwo,
            Long tuteeProfileIdThree,
            TutoringStatus tutoringStatusThree,
            Pageable pageable
    );
}
