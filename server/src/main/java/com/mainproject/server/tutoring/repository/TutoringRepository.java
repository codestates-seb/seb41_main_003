package com.mainproject.server.tutoring.repository;

import com.mainproject.server.constant.TutoringStatus;
import com.mainproject.server.tutoring.entity.Tutoring;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TutoringRepository extends JpaRepository<Tutoring, Long> {
    Page<Tutoring> findAllByTutoringStatusOrTutoringStatusAndTutorProfileId(
            TutoringStatus tutoringStatusOne,
            TutoringStatus tutoringStatusTwo,
            Long tutorId,
            Pageable pageable
    );

    Page<Tutoring> findAllByTutoringStatusOrTutoringStatusAndTuteeProfileId(
            TutoringStatus tutoringStatusOne,
            TutoringStatus tutoringStatusTwo,
            Long tuteeId,
            Pageable pageable
    );
}
