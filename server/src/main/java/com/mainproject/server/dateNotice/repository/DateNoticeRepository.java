package com.mainproject.server.dateNotice.repository;

import com.mainproject.server.dateNotice.entity.DateNotice;
import com.mainproject.server.tutoring.entity.Tutoring;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DateNoticeRepository extends JpaRepository<DateNotice, Long> {

    Page<DateNotice> findAllByTutoring(Tutoring tutoring, Pageable pageable);
}
