package com.mainproject.server.dateNotice.entity;


import com.mainproject.server.audit.Auditable;
import lombok.*;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
@Builder
@AllArgsConstructor
public class Notice extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long noticeId;


    @Column(nullable = false, columnDefinition = "LONGTEXT")
    @Setter
    private String noticeBody;
}
