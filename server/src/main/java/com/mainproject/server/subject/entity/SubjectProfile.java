package com.mainproject.server.subject.entity;

import com.mainproject.server.profile.entity.Profile;
import lombok.*;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
@AllArgsConstructor
@Builder
public class SubjectProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long subjectProfileId;

    @Column(nullable = false, columnDefinition = "LONGTEXT")
    @Setter
    private String content;

    /* 연관 관계 매핑 */

    @ToString.Exclude
    @ManyToOne(optional = false, fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    @Setter
    private Profile profile;

    @ToString.Exclude
    @ManyToOne(optional = false, fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    @Setter
    private Subject subject;

    public SubjectProfile(Profile profile, Subject subject, String content) {
        addProfile(profile);
        addSubject(subject);
        this.content = content;
    }

    /* 연관 관계 편의 메소드 */

    public void addProfile(Profile profile) {
        setProfile(profile);
        profile.addSubjectProfile(this);
    }

    public void addSubject(Subject subject) {
        setSubject(subject);
        subject.addSubjectProfile(this);
    }

}
