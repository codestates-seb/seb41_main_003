package com.mainproject.server.dateNotice.repository;

import com.mainproject.server.dateNotice.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
}
