package com.mainproject.server.tutoring.entity;

import com.mainproject.server.audit.Auditable;
import com.mainproject.server.constant.TutoringStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@NoArgsConstructor
@Entity
public class Tutoring extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tutoringId;

    private TutoringStatus tutoringStatus;
}
