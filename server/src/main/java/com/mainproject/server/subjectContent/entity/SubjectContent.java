package com.mainproject.server.subjectContent.entity;

import com.mainproject.server.audit.Auditable;
import com.mainproject.server.profile.entity.Profile;
import com.mainproject.server.user.entity.User;
import lombok.*;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
@Builder
@AllArgsConstructor
public class SubjectContent extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long subjectContentId;

    @Column(nullable = false)
    @Setter
    private Long subjectTitle;

    @Column(nullable = false, columnDefinition = "LONGTEXT")
    @Setter
    private String content;

    /* 연관 관계 매핑 */
    @ToString.Exclude
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Setter
    private Profile profile;

    /* 연관 관계 편의 메소드 */
    public void addProfile(Profile profile) {
        setProfile(profile);
        profile.addSubjectContent(this);
    }
}
