package com.mainproject.server.image.entity;

import com.mainproject.server.audit.Auditable;
import lombok.*;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
@Builder
@AllArgsConstructor
public class UserImage extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userImageId;

    @Column(nullable = false)
    @Setter
    private String url;
}
