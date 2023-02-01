package com.mainproject.server.subject.entity;

import com.mainproject.server.audit.Auditable;
import lombok.*;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@NoArgsConstructor
@Entity
@Builder
@AllArgsConstructor
public class Subject extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long subjectId;

    @Column(nullable = false)
    @Setter
    private String subjectTitle;

    /* 연관 관계 매핑 */
    @ToString.Exclude
    @OrderBy("subjectProfileId")
    @OneToMany(mappedBy = "subject", cascade = CascadeType.REMOVE)
    @Setter
    private Set<SubjectProfile> subjectProfiles = new LinkedHashSet<>();

    /* 연관 관계 편의 메소드 */

    public void addSubjectProfile(SubjectProfile subjectProfile) {
        this.subjectProfiles.add(subjectProfile);
    }
}
