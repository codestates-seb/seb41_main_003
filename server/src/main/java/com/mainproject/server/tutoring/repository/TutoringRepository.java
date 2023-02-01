package com.mainproject.server.tutoring.repository;

import com.mainproject.server.constant.TutoringStatus;
import com.mainproject.server.profile.entity.Profile;
import com.mainproject.server.profile.entity.QProfile;
import com.mainproject.server.profile.repository.custom.CustomProfileRepository;
import com.mainproject.server.tutoring.entity.QTutoring;
import com.mainproject.server.tutoring.entity.Tutoring;
import com.mainproject.server.tutoring.repository.custom.CustomTutoringRepository;
import com.querydsl.core.types.dsl.StringExpression;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

public interface TutoringRepository extends JpaRepository<Tutoring, Long>,
        CustomTutoringRepository,
        QuerydslPredicateExecutor<Tutoring>,
        QuerydslBinderCustomizer<QTutoring> {

    @Override
    default void customize(QuerydslBindings bindings, QTutoring root) {
        bindings.excludeUnlistedProperties(true);
        bindings.including(root.latestNoticeBody);
        bindings.bind(root.latestNoticeBody).as("latestNoticeBody").first(StringExpression::containsIgnoreCase);
    }
}
