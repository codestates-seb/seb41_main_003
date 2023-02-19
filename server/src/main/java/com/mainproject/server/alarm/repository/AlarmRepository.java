package com.mainproject.server.alarm.repository;

import com.mainproject.server.alarm.entity.Alarm;
import com.mainproject.server.profile.entity.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlarmRepository extends JpaRepository<Alarm, Long> {

    Page<Alarm> findAllByProfile(Profile profile, Pageable pageable);
}
