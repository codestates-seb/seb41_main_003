package com.mainproject.server.profile.repository;

import com.mainproject.server.profile.entity.Profile;
import com.mainproject.server.profile.entity.QProfile;
import com.mainproject.server.profile.repository.custom.CustomProfileRepository;
import com.querydsl.core.types.dsl.StringExpression;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

public interface ProfileRepository extends JpaRepository<Profile, Long>,
        CustomProfileRepository,
        QuerydslPredicateExecutor<Profile>,
        QuerydslBinderCustomizer<QProfile> {
    @Override
    default void customize(QuerydslBindings bindings, QProfile root) {
        bindings.excludeUnlistedProperties(true);
        bindings.including(root.school);
        bindings.bind(root.school).as("school").first(StringExpression::containsIgnoreCase);
    }
}
