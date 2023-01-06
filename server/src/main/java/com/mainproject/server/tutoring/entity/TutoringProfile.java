package com.mainproject.server.tutoring.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@NoArgsConstructor
@Entity
public class TutoringProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tutoringProfileId;


}
