package com.mainproject.server.dateNotice.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mainproject.server.audit.Auditable;
import com.mainproject.server.tutoring.entity.Tutoring;
import lombok.*;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
@Builder
@AllArgsConstructor
public class DateNotice extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long dateNoticeId;

    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Setter
    private LocalDateTime noticeAt;

    /* 연관 관계 매핑 */
    @ToString.Exclude
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Setter
    private Homework homework;

    @ToString.Exclude
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Setter
    private Notice notice;

    @ToString.Exclude
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Setter
    private Schedule schedule;

    @ToString.Exclude
    @ManyToOne(optional = false, fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    @Setter
    private Tutoring tutoring;


    /* 연관 관계 편의 메소드 */

    public void addHomework(Homework homework) {
        setHomework(homework);
    }

    public void addNotice(Notice notice) {
        setNotice(notice);
    }

    public void addSchedule(Schedule schedule) {
        setSchedule(schedule);
    }

    public void addTutoring(Tutoring tutoring) {
        setTutoring(tutoring);
        tutoring.addDateNotice(this);
    }

}
