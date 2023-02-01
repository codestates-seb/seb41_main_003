package com.mainproject.server.message.entity;

import com.mainproject.server.audit.Auditable;
import com.mainproject.server.constant.MessageStatus;
import com.mainproject.server.profile.entity.Profile;
import lombok.*;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@NoArgsConstructor
@Entity
@Builder
@AllArgsConstructor
public class MessageRoom extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long messageRoomId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @Setter
    private MessageStatus messageStatus;

    @Column(nullable = true)
    @Setter
    private Long tutoringId;

    @Column(nullable = true)
    @Setter
    private String lastMessage;

    @Column(nullable = true)
    @Setter
    private Long lastSenderId;

    /* 연관 관계 매핑 */

    @ToString.Exclude
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @Setter
    private Profile tutor;

    @ToString.Exclude
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @Setter
    private Profile tutee;

    @ToString.Exclude
    @OrderBy("messageId")
    @OneToMany(mappedBy = "messageRoom", cascade = CascadeType.ALL)
    @Setter
    private Set<Message> messages = new LinkedHashSet<>();

    /* 연관 관계 편의 메소드 */
    public void addMessage(Message message) {
        this.lastMessage = message.getMessageContent();
        this.lastSenderId = message.getSender().getProfileId();
        this.messages.add(message);
    }

    public void addTutor(Profile tutor) {
        setTutor(tutor);
    }

    public void addTutee(Profile tutee) {
        setTutee(tutee);
    }


}
