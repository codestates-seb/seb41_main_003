package com.mainproject.server.tutoring.repository;

import com.mainproject.server.constant.TutoringStatus;
import com.mainproject.server.tutoring.entity.Tutoring;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TutoringRepository extends JpaRepository<Tutoring, Long> {
    Page<Tutoring> findAllByTutoringStatusAndTutorProfileId(
            TutoringStatus tutoringStatus,
            Long tutorId,
            Pageable pageable
    );

    Page<Tutoring> findAllByTutoringStatusAndTuteeProfileId(
            TutoringStatus tutoringStatus,
            Long tuteeId,
            Pageable pageable
    );
}
