package com.mainproject.server.message.entity;

import com.mainproject.server.audit.Auditable;
import com.mainproject.server.tuteeProfile.entity.TuteeProfile;
import com.mainproject.server.tutorProfile.entity.TutorProfile;
import lombok.*;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
@Builder
@AllArgsConstructor
public class Message extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long messageId;

    @Column(nullable = false)
    @Setter
    private String messageTitle;

    @Column(nullable = false,columnDefinition = "LONGTEXT")
    @Setter
    private String messageContent;

    /* 연관 관계 매핑 */
    @ToString.Exclude
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "TUTOR_PROFILE_ID")
    @Setter
    private TutorProfile tutorProfile;

    @ToString.Exclude
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "TUTEE_PROFILE_ID")
    @Setter
    private TuteeProfile tuteeProfile;

    /* 연관 관계 편의 메소드 */
    public void addTutorProfile(TutorProfile tutorProfile) {
        setTutorProfile(tutorProfile);
        tutorProfile.addMessage(this);
    }

    public void addTuteeProfile(TuteeProfile tuteeProfile) {
        setTuteeProfile(tuteeProfile);
        tuteeProfile.addMessage(this);
    }

}
