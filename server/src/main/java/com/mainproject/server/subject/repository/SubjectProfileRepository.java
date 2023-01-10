package com.mainproject.server.subject.repository;

import com.mainproject.server.subject.entity.SubjectProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubjectProfileRepository extends JpaRepository<SubjectProfile, Long> {
}
