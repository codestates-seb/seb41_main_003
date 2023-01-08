package com.mainproject.server.image.entity;

import com.mainproject.server.audit.Auditable;
import com.mainproject.server.tutorProfile.entity.TutorProfile;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class VerifyImage extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long verifyImageId;

    @Column(nullable = false)
    @Setter
    private String url;

    /* 연관 관계 매핑 */
    @ToString.Exclude
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Setter
    private TutorProfile tutorProfile;

    /* 연관 관계 편의 메소드 */

    public void addTutorProfile(TutorProfile tutorProfile) {
        setTutorProfile(tutorProfile);
        tutorProfile.addVerifyImage(this);
    }

}
