package com.mainproject.server.dateNotice.entity;

import com.mainproject.server.audit.Auditable;
import com.mainproject.server.constant.HomeworkStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@NoArgsConstructor
@Entity
public class Homework extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long homeworkId;

    private String homeworkBody;

    private HomeworkStatus homeworkStatus;
}
