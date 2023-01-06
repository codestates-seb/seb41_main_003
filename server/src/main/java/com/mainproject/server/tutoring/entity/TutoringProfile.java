package com.mainproject.server.tutoring.entity;

import com.mainproject.server.profile.entity.Profile;
import lombok.*;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class TutoringProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tutoringProfileId;

    /* 연관 관계 매핑 */

    @ToString.Exclude
    @ManyToOne(optional = false, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Setter
    private Tutoring tutoring;

    @ToString.Exclude
    @ManyToOne(optional = false, fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    @Setter
    private Profile profile;

    /* 연관 관계 편의 메소드 */
    public void addTutoring(Tutoring tutoring) {
        setTutoring(tutoring);
        tutoring.addTutoringProfile(this);
    }

    public void addProfile(Profile profile) {
        setProfile(profile);
        profile.addTutoringProfile(this);
    }

}
