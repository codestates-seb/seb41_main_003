package com.mainproject.server.auth.redis.repository;

import com.mainproject.server.auth.redis.entity.RefreshEntity;
import org.springframework.data.repository.CrudRepository;

public interface RefreshRepository extends CrudRepository<RefreshEntity, String> {
}
