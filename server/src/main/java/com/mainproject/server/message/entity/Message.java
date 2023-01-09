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

    @Column(nullable = false,columnDefinition = "LONGTEXT")
    @Setter
    private String messageContent;

    /* 연관 관계 매핑 */
    @ToString.Exclude
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Setter
    private Profile sender;

    @ToString.Exclude
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Setter
    private Profile receiver;

    /* 연관 관계 편의 메소드 */
    public void addSender(Profile sender) {
        setSender(sender);
        sender.addSendMessage(this);
    }

    public void addReceiver(Profile receiver) {
        setReceiver(receiver);
        receiver.addReceiveMessage(this);
    }

}
