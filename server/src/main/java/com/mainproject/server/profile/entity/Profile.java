package com.mainproject.server.profile.entity;

import com.mainproject.server.audit.Auditable;
import com.mainproject.server.constant.ProfileStatus;
import com.mainproject.server.constant.WantedStatus;
import com.mainproject.server.image.entity.ProfileImage;
import com.mainproject.server.review.entity.Review;
import com.mainproject.server.subject.entity.SubjectProfile;
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

    @Setter
    @Column(nullable = false)
    private String name;

    @Setter
    @Column(nullable = false)
    private double rate;

    @Setter
    @Column(nullable = false)
    private String bio;

    @Setter
    @Column(nullable = false, columnDefinition = "LONGTEXT")
    private String wantDate;

    @Setter
    @Column(nullable = false, columnDefinition = "LONGTEXT")
    private String pay;

    @Setter
    @Column(nullable = false, columnDefinition = "LONGTEXT")
    private String way;

    @Setter
    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private ProfileStatus profileStatus;

    @Setter
    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private WantedStatus wantedStatus;

    @Setter
    @Column(nullable = false)
    private String gender;

    @Setter
    @Column(nullable = false)
    private String school;

    @Setter
    @Column(nullable = false, columnDefinition = "LONGTEXT")
    private String character;

    @Setter
    @Column(nullable = false, columnDefinition = "LONGTEXT")
    private String preTutoring;

    @Setter
    @Column(nullable = true, columnDefinition = "LONGTEXT")
    private String difference;


    /* 연관 관계 매핑*/
    @ToString.Exclude
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Setter
    private User user;


    @ToString.Exclude
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Setter
    private ProfileImage profileImage;

    @ToString.Exclude
    @OrderBy("reviewId")
    @OneToMany(mappedBy = "profile", cascade = CascadeType.ALL)
    @Setter
    private Set<Review> reviews = new LinkedHashSet<>();

    @ToString.Exclude
    @OrderBy("subjectProfileId")
    @OneToMany(mappedBy = "profile", cascade = CascadeType.ALL)
    @Setter
    private Set<SubjectProfile> subjectProfiles = new LinkedHashSet<>();



    /* 연관 관계 편의 메소드 */

    public void addUserImage(ProfileImage profileImage) {
        setProfileImage(profileImage);
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

}
