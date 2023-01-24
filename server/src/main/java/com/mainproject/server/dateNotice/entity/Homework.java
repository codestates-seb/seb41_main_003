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
    private HomeworkStatus homeworkStatus = HomeworkStatus.PROGRESS;


    /* 연관 관계 매핑 */
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "DATE_NOTICE_ID")
    @Setter
    private DateNotice dateNotice;


    /* 연관 관계 편의 메소드 */

    public void addDateNotice(DateNotice dateNotice) {
        setDateNotice(dateNotice);
    }


}
