package com.project.fyst.global.redis.repository;

import com.project.fyst.global.redis.dto.CacheRefreshToken;
import org.springframework.data.repository.CrudRepository;

public interface RefreshTokenRedisRepository extends CrudRepository<CacheRefreshToken, String> {
}
