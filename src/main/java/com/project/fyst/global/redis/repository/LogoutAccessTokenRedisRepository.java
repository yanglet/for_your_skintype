package com.project.fyst.global.redis.repository;

import com.project.fyst.global.redis.dto.LogoutAccessToken;
import org.springframework.data.repository.CrudRepository;

public interface LogoutAccessTokenRedisRepository extends CrudRepository<LogoutAccessToken, String> {
}
