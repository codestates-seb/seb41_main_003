package com.mainproject.server.tutoring.repository;

import com.mainproject.server.tutoring.entity.Tutoring;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TutoringRepository extends JpaRepository<Tutoring, Long> {
    Page<Tutoring> findAllByTutorProfileIdOrTuteeProfileId(Long tutorId, Long tuteeId, Pageable pageable);
}
