package com.mainproject.server.image.entity;

import com.mainproject.server.audit.Auditable;
import lombok.*;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
@Builder
@AllArgsConstructor
public class ProfileImage extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long profileImageId;

    @Column(nullable = false)
    @Setter
    private String url;
}
