package com.mainproject.server.review.entity;

import com.mainproject.server.audit.Auditable;
import com.mainproject.server.profile.entity.Profile;
import com.mainproject.server.tutoring.entity.Tutoring;
import lombok.*;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
@Builder
@AllArgsConstructor
public class Review extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewId;

    @Column(nullable = false)
    @Setter
    private int professional;

    @Column(nullable = false)
    @Setter
    private int readiness;

    @Column(nullable = false)
    @Setter
    private int explanation;

    @Column(nullable = false)
    @Setter
    private int punctuality;

    @Column(nullable = true, columnDefinition = "LONGTEXT")
    @Setter
    private String reviewBody;

    /* 연관 관계 매핑 */

    @ToString.Exclude
    @ManyToOne(optional = false, fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    @Setter
    private Profile profile;

    /* 연관 관계 편의 메소드 */

    public void addProfile(Profile profile) {
        setProfile(profile);
        profile.addReview(this);
    }
}
