package com.mainproject.server.message.entity;

import com.mainproject.server.audit.Auditable;
import com.mainproject.server.profile.entity.Profile;
import lombok.*;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
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
    private Profile sender;

    @ToString.Exclude
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Setter
    private Profile receiver;

    /* 연관 관계 편의 메소드 */
    public void addMessageRoom(MessageRoom messageRoom) {
        setMessageRoom(messageRoom);
        messageRoom.addMessage(this);
    }

    public void addSender (Profile sender) {
        setSender(sender);
        this.senderName = sender.getName();
    }

    public void addReceiver (Profile tuteeProfile) {
        setReceiver(receiver);
        this.receiverName = receiver.getName();
    }
}
