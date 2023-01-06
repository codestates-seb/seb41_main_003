package com.mainproject.server.dateNotice.entity;

import com.mainproject.server.audit.Auditable;
import com.mainproject.server.constant.HomeworkStatus;
import lombok.*;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
@Builder
@AllArgsConstructor
public class Homework extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long homeworkId;

    @Column(nullable = false, columnDefinition = "LONGTEXT")
    @Setter
    private String homeworkBody;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @Setter
    private HomeworkStatus homeworkStatus;
}
