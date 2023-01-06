package com.mainproject.server.profile.entity;

import com.mainproject.server.audit.Auditable;
import com.mainproject.server.dateNotice.entity.Schedule;
import com.mainproject.server.review.entity.Review;
import com.mainproject.server.subject.entity.SubjectProfile;
import com.mainproject.server.tuteeProfile.entity.TuteeProfile;
import com.mainproject.server.tutorProfile.entity.TutorProfile;
import com.mainproject.server.tutoring.entity.TutoringProfile;
import com.mainproject.server.user.entity.User;
import lombok.*;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@NoArgsConstructor
@Entity
@Builder
@AllArgsConstructor
public class Profile extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long profileId;


    /* 연관 관계 매핑*/
    @ToString.Exclude
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Setter
    private TuteeProfile tuteeProfile;

    @ToString.Exclude
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Setter
    private TutorProfile tutorProfile;

    @ToString.Exclude
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Setter
    private User user;

    @ToString.Exclude
    @OrderBy("reviewId")
    @OneToMany(mappedBy = "profile", cascade = CascadeType.REMOVE)
    @Setter
    private Set<Review> reviews = new LinkedHashSet<>();

    @ToString.Exclude
    @OrderBy("subjectProfileId")
    @OneToMany(mappedBy = "profile", cascade = CascadeType.REMOVE)
    @Setter
    private Set<SubjectProfile> subjectProfiles = new LinkedHashSet<>();

    @ToString.Exclude
    @OrderBy("tutoringProfileId")
    @OneToMany(mappedBy = "tutoring", cascade = CascadeType.DETACH)
    @Setter
    private Set<TutoringProfile> tutoringProfiles = new LinkedHashSet<>();

    /* 연관 관계 편의 메소드 */

    public void addTuteeProfile(TuteeProfile tuteeProfile) {
        setTuteeProfile(tuteeProfile);
        tuteeProfile.setProfile(this);
    }

    public void addTutorProfile(TutorProfile tutorProfile) {
        setTuteeProfile(tuteeProfile);
        tutorProfile.setProfile(this);
    }

    public void addUser(User user) {
        setUser(user);
        user.addProfile(this);
    }

    public void addReview(Review review) {
        this.reviews.add(review);
    }

    public void addSubjectProfile(SubjectProfile subjectProfile) {
        this.subjectProfiles.add(subjectProfile);
    }

    public void addTutoringProfile(TutoringProfile tutoringProfile) {
        this.tutoringProfiles.add(tutoringProfile);
    }

}
