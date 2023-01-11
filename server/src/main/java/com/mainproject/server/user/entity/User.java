package com.mainproject.server.user.entity;

import com.mainproject.server.audit.Auditable;
import com.mainproject.server.constant.LoginType;
import com.mainproject.server.constant.UserStatus;
import com.mainproject.server.profile.entity.Profile;
import com.mainproject.server.review.entity.Review;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "users")
@Builder
@AllArgsConstructor
public class User extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter
    private Long userId;

    @Column(nullable = false)
    @Setter
    private String email;

    @Column(nullable = false)
    @Setter
    private String nickName;

    @Column(nullable = false)
    @Setter
    private String password;

    @Column(nullable = true)
    @Setter
    private String secondPassword;

    @Column(nullable = true)
    @Setter
    private String phoneNumber;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @Setter
    private LoginType loginType;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @Setter
    private UserStatus userStatus;

    @ElementCollection(fetch = FetchType.EAGER)
    @Setter
    private List<String> roles = new ArrayList<>();

    /* 연관 관계 매핑 */

    @ToString.Exclude
    @OrderBy("profileId")
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    @Setter
    private Set<Profile> profiles = new LinkedHashSet<>();

    /* 연관 관계 편의 메소드 */

    public void addProfile(Profile profile) {
        profiles.add(profile);
    }
}
