package com.project.fyst.global.redis.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

import javax.persistence.Id;

@Getter
@RedisHash("refreshToken")
@AllArgsConstructor
@Builder
public class CacheRefreshToken {

    @Id
    private String id;

    private String refreshToken;

    @TimeToLive
    private Long expiration;

    public static CacheRefreshToken of(String username,
                                       String refreshToken,
                                       Long remainingMilliSeconds) {
        return CacheRefreshToken.builder()
                .id(username)
                .refreshToken(refreshToken)
                .expiration(remainingMilliSeconds / 1000)
                .build();
    }
}
