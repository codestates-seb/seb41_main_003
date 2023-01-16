package com.mainproject.server.review.entity;

import com.mainproject.server.audit.Auditable;
import com.mainproject.server.profile.entity.Profile;
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
    @ManyToOne(optional = false, fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @Setter
    private Profile tutor;

    @ToString.Exclude
    @ManyToOne(optional = false, fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @Setter
    private Profile tutee;

    /* 연관 관계 편의 메소드 */

    public void addTutor(Profile tutor) {
        setTutor(tutor);
        tutor.addReview(this);
    }

    public void addTutee(Profile tutee) {
        setTutee(tutee);
    }
}
