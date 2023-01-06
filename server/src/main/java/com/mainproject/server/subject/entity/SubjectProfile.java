package com.mainproject.server.subject.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@NoArgsConstructor
@Entity
public class SubjectProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long subjectProfileId;
}
