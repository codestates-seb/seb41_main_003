package com.mainproject.server.tutoring.entity;

import com.mainproject.server.audit.Auditable;
import com.mainproject.server.constant.TutoringStatus;
import com.mainproject.server.dateNotice.entity.DateNotice;
import com.mainproject.server.profile.entity.Profile;
import com.mainproject.server.review.entity.Review;
import lombok.*;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@NoArgsConstructor
@Entity
@AllArgsConstructor
@Builder
public class Tutoring extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tutoringId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @Setter
    private TutoringStatus tutoringStatus;


    /* 연관 관계 매핑*/

    @ToString.Exclude
    @ManyToOne(optional = false, fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    @Setter
    private Profile tutor;

    @ToString.Exclude
    @ManyToOne(optional = false, fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    @Setter
    private Profile tutee;

    @ToString.Exclude
    @OrderBy("dateNoticeId")
    @OneToMany(mappedBy = "tutoring", cascade = CascadeType.ALL)
    @Setter
    private Set<DateNotice> dateNotices = new LinkedHashSet<>();

    @ToString.Exclude
    @OrderBy("reviewId")
    @OneToMany(mappedBy = "tutoring", cascade = CascadeType.DETACH)
    @Setter
    private Set<Review> reviews = new LinkedHashSet<>();


    /* 연관 관계 편의 메소드 */

    public void addDateNotice(DateNotice dateNotice) {
        this.dateNotices.add(dateNotice);
    }

    public void addReview(Review reviews) {
        this.reviews.add(reviews);
    }

    public void addTutor(Profile tutor) {
        setTutor(tutor);
    }

    public void addTutee(Profile tutee) {
        setTutor(tutee);
    }

}
