package com.mainproject.server.dateNotice.repository;

import com.mainproject.server.dateNotice.entity.Notice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeRepository extends JpaRepository<Notice, Long> {
}
