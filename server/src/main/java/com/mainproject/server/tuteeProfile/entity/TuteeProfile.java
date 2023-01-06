package com.mainproject.server.tuteeProfile.entity;

import com.mainproject.server.audit.Auditable;
import com.mainproject.server.constant.TuteeStatus;
import com.mainproject.server.constant.WantedStatus;
import com.mainproject.server.image.entity.UserImage;
import com.mainproject.server.image.entity.VerifyImage;
import com.mainproject.server.message.entity.Message;
import com.mainproject.server.profile.entity.Profile;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Getter
@NoArgsConstructor
@Entity
@AllArgsConstructor
@Builder
public class TuteeProfile extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tuteeProfileId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @Setter
    private TuteeStatus tuteeStatus;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @Setter
    private WantedStatus wantedStatus;

    @Column(nullable = false)
    @Setter
    private String profileTitle;

    @Column(nullable = false)
    @Setter
    private String name;

    @Column(nullable = false, columnDefinition = "LONGTEXT")
    @Setter
    private String intro;

    @Column(nullable = false)
    @Setter
    private String line;

    @Column(nullable = false, columnDefinition = "LONGTEXT")
    @Setter
    private String wantWay;

    @Column(nullable = false)
    @Setter
    private String gender;

    @Column(nullable = false)
    @Setter
    private String pay;

    @Column(nullable = false)
    @Setter
    private String wantDate;

    /* 연관 관계 매핑*/

    @ToString.Exclude
    @OneToOne(mappedBy = "tuteeProfile", fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    @Setter
    private Profile profile;

    @ToString.Exclude
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Setter
    private UserImage userImage;

    @ToString.Exclude
    @OrderBy("messageId")
    @OneToMany(mappedBy = "tuteeProfile", fetch = FetchType.LAZY)
    @Setter
    private Set<Message> messages = new LinkedHashSet<>();

    /* 연관 관계 편의 메서드 */
    public void addMessage(Message message) {
        messages.add(message);
    }

    public void addProfile(Profile profile) {
        setProfile(profile);
        profile.setTuteeProfile(this);
    }

    public void addUserImage(UserImage userImage) {
        setUserImage(userImage);
    }

}
