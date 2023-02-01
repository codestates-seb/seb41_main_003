package com.mainproject.server.profile.repository.custom;

import com.mainproject.server.constant.ErrorCode;
import com.mainproject.server.constant.ProfileStatus;
import com.mainproject.server.constant.WantedStatus;
import com.mainproject.server.exception.ServiceLogicException;
import com.mainproject.server.profile.dto.ProfileQueryDto;
import com.mainproject.server.profile.entity.Profile;
import com.mainproject.server.profile.entity.QProfile;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;
import java.util.Optional;

public class CustomProfileRepositoryImpl
        extends QuerydslRepositorySupport implements CustomProfileRepository {

    public CustomProfileRepositoryImpl() {
        super(Profile.class);
    }

    @Override
    public Page<ProfileQueryDto> findQueryProfile(
            String key,
            String[] subjects,
            String search,
            WantedStatus wantedStatus,
            Pageable pageable
    ) {
        QProfile profile = QProfile.profile;

        JPQLQuery<ProfileQueryDto> query = from(profile)
                .select(
                        Projections.constructor(
                                ProfileQueryDto.class,
                                profile.profileId,
                                profile.profileStatus,
                                profile.wantedStatus,
                                profile.name,
                                profile.rate,
                                profile.school,
                                profile.bio,
                                profile.profileImage.profileImageId,
                                profile.profileImage.url,
                                profile.subjectString,
                                profile.createAt,
                                profile.updateAt
                        ));
        if (wantedStatus != null) {
            query.where(
                    profile.wantedStatus.eq(wantedStatus)
            );
        }
        if (key != null && !key.isBlank()) {
            query.where(
                    profile.profileStatus.eq(ProfileStatus.valueOf(key))
            );
        }
        if (subjects != null) {
            for (String subject : subjects) {
                if (subject != null && !subject.isBlank()) {
                    query.where(profile.subjectString.containsIgnoreCase(subject));
                }
            }
        }
        if (search != null && !search.isBlank()) {
            query.where(
                    profile.name.containsIgnoreCase(search)
                            .or(profile.bio.containsIgnoreCase(search))
                            .or(profile.school.containsIgnoreCase(search))
                            .or(profile.subjectString.containsIgnoreCase(search))
            );
        }
        List<ProfileQueryDto> profileList = Optional.ofNullable(getQuerydsl())
                .orElseThrow(() -> new ServiceLogicException(
                        ErrorCode.DATA_ACCESS_ERROR))
                .applyPagination(pageable, query)
                .fetch();
        return new PageImpl<>(profileList, pageable, query.fetchCount());
    }
}
