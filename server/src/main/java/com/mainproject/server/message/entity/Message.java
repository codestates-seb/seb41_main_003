package com.mainproject.server.message.entity;

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
public class Message extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long messageId;

    @Column(nullable = false)
    private String receiverName;

    @Column(nullable = false)
    private String senderName;

    @Column(nullable = false,columnDefinition = "LONGTEXT")
    @Setter
    private String messageContent;

    /* 연관 관계 매핑 */
    @ToString.Exclude
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Setter
    private MessageRoom messageRoom;

    @ToString.Exclude
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Setter
    private Profile tutor;

    @ToString.Exclude
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Setter
    private Profile tutee;

    /* 연관 관계 편의 메소드 */
    public void addMessageRoom(MessageRoom messageRoom) {
        setMessageRoom(messageRoom);
        messageRoom.addMessage(this);
    }

    public void addTutorProfile(Profile tutorProfile) {
        setTutor(tutorProfile);
    }

    public void addTuteeProfile(Profile tuteeProfile) {
        setTutee(tuteeProfile);
    }
}
