package com.mainproject.server.dateNotice.repository;

import com.mainproject.server.dateNotice.entity.Homework;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HomeworkRepository extends JpaRepository<Homework, Long> {
}
