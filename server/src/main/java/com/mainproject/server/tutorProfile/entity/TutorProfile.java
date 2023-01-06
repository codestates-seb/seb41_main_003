package com.mainproject.server.tutorProfile.entity;

import com.mainproject.server.audit.Auditable;
import com.mainproject.server.constant.WantedStatus;
import com.mainproject.server.dateNotice.entity.DateNotice;
import com.mainproject.server.image.entity.UserImage;
import com.mainproject.server.image.entity.VerifyImage;
import com.mainproject.server.message.entity.Message;
import com.mainproject.server.profile.entity.Profile;
import com.mainproject.server.tuteeProfile.entity.TuteeProfile;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@NoArgsConstructor
@Entity
@AllArgsConstructor
@Builder
public class TutorProfile extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tutorProfileId;

    @Column(nullable = false)
    @Setter
    private String profileTitle;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @Setter
    private WantedStatus wantedStatus;

    @Column(nullable = false)
    @Setter
    private String name;

    @Column(nullable = false)
    @Setter
    private String tutoringNum;

    @Column(nullable = false)
    @Setter
    private String pay;

    @Column(nullable = false, columnDefinition = "LONGTEXT")
    @Setter
    private String intro;

    @Column(nullable = false)
    @Setter
    private String univ;

    @Column(nullable = false)
    @Setter
    private String character;

    @Column(nullable = false, columnDefinition = "LONGTEXT")
    @Setter
    private String way;

    @Column(nullable = false)
    @Setter
    private String wantDate;

    @Column(nullable = false)
    @Setter
    private int age;

    @Column(nullable = false)
    @Setter
    private String passType;

    @Column(nullable = false,columnDefinition = "LONGTEXT")
    @Setter
    private String difference;

    /* 연관 관계 매핑*/

    @ToString.Exclude
    @OneToOne(mappedBy = "tutorProfile", fetch = FetchType.EAGER)
    @Setter
    private Profile profile;

    @ToString.Exclude
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Setter
    private UserImage userImage;

    @ToString.Exclude
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Setter
    private VerifyImage verifyImage;

    @ToString.Exclude
    @OrderBy("messageId")
    @OneToMany(mappedBy = "tutorProfile", fetch = FetchType.LAZY)
    @Setter
    private Set<Message> messages = new LinkedHashSet<>();

    /* 연관 관계 편의 메소드 */
    public void addMessage(Message message) {
        messages.add(message);
    }

    public void addProfile(Profile profile) {
        setProfile(profile);
        profile.setTutorProfile(this);
    }

    public void addUserImage(UserImage userImage) {
        setUserImage(userImage);
    }

    public void addVerifyImage(VerifyImage verifyImage) {
        setVerifyImage(verifyImage);
    }

}
