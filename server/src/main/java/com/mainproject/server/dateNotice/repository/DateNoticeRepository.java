package com.mainproject.server.dateNotice.repository;

import com.mainproject.server.dateNotice.entity.DateNotice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DateNoticeRepository extends JpaRepository<DateNotice, Long> {
}
