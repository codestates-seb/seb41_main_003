package com.mainproject.server.tutorProfile.entity;

import com.mainproject.server.audit.Auditable;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@NoArgsConstructor
@Entity
public class TutorProfile extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tutorProfileId;

    private String profileTitle;

    private String wantedStatus;

    private String name;

    private String tutoringNum;

    private String pay;

    private String intro;

    private String univ;

    private String character;

    private String way;

    private String wantDate;

    private int age;

    private String passType;

    private String difference;

}
