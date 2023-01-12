package com.mainproject.server.subject.repository;

import com.mainproject.server.profile.entity.Profile;
import com.mainproject.server.subject.entity.SubjectProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubjectProfileRepository extends JpaRepository<SubjectProfile, Long> {
    void deleteByProfileProfileId(Long profileId);
}
