package com.mainproject.server.image.entity;

import com.mainproject.server.audit.Auditable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class VerifyImage extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long verifyImageId;

    @Column(nullable = false)
    @Setter
    private String url;

}
