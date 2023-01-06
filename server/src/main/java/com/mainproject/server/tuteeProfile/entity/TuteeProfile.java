package com.mainproject.server.tuteeProfile.entity;

import com.mainproject.server.audit.Auditable;
import com.mainproject.server.constant.TuteeStatus;
import com.mainproject.server.constant.WantedStatus;
import com.mainproject.server.image.entity.UserImage;
import com.mainproject.server.image.entity.VerifyImage;
import com.mainproject.server.message.entity.Message;
import com.mainproject.server.profile.entity.Profile;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@NoArgsConstructor
@Entity
public class TuteeProfile extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tuteeProfileId;

    private String profileTitle;

    private String name;

    private String intro;

    private String line;

    private String wantWay;

    private String gender;

    private String pay;

    private String wantDate;

}
