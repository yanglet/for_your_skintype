package com.project.fyst.domain.auth.service;

import com.project.fyst.domain.auth.exception.MemberDuplicateException;
import com.project.fyst.domain.auth.exception.MemberNotFoundException;
import com.project.fyst.domain.auth.exception.PasswordMismatchException;
import com.project.fyst.domain.member.entity.Member;
import com.project.fyst.domain.member.repository.MemberRepository;
import com.project.fyst.domain.member.request.MemberLoginRequest;
import com.project.fyst.domain.member.response.MemberLoginResponse;
import com.project.fyst.global.config.CacheKey;
import com.project.fyst.global.jwt.dto.AccessToken;
import com.project.fyst.global.jwt.service.JwtTokenProvider;
import com.project.fyst.global.redis.dto.CacheRefreshToken;
import com.project.fyst.global.redis.dto.LogoutAccessToken;
import com.project.fyst.global.redis.repository.LogoutAccessTokenRedisRepository;
import com.project.fyst.global.redis.repository.RefreshTokenRedisRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final RefreshTokenRedisRepository refreshTokenRedisRepository;
    private final LogoutAccessTokenRedisRepository logoutAccessTokenRedisRepository;

    public MemberLoginResponse login(MemberLoginRequest loginRequest){
        log.info("AuthService.login()");
        Member member = memberRepository.findByEmail(loginRequest.getEmail()).
                orElseThrow(MemberNotFoundException::new);
        checkPassword(loginRequest.getPassword(), member.getPassword());

        AccessToken accessToken = jwtTokenProvider.generateAccessTokenBy(member);
        String refreshToken = jwtTokenProvider.generateRefreshToken(member);

        saveRefreshToken(member, refreshToken);

        return new MemberLoginResponse(accessToken, refreshToken);
    }

    public Member join(Member member){
        isValidateDuplicateMember(member);
        return memberRepository.save(member);
    }

    public CacheRefreshToken saveRefreshToken(Member member,
                                              String refreshToken){
        return refreshTokenRedisRepository.save(CacheRefreshToken.of(member.getEmail(),
                refreshToken,
                jwtTokenProvider.getJWT_REFRESH_TOKEN_VALIDITY()));
    }

    @CacheEvict(value = CacheKey.USER, key = "#username")
    public void logout(String accessToken, String username){ // username = member.getEmail()
        SecurityContextHolder.clearContext();
        Date now = new Date();
        refreshTokenRedisRepository.deleteById(username);
        logoutAccessTokenRedisRepository.save(LogoutAccessToken.of(
                accessToken,
                username,
                jwtTokenProvider.getAccessTokenExpiredTime(accessToken) - now.getTime()
        ));
    }

    public String getUsername(String accessToken){
        return jwtTokenProvider.findMemberByToken(accessToken).getEmail();
    }

    private void checkPassword(String loginPassword, String password) {
        log.info("AuthService.checkPassword()");
        if( !passwordEncoder.matches(loginPassword, password) ){
            throw new PasswordMismatchException();
        }
    }

    public AccessToken getAccessTokenBy(String refreshToken){
        log.info("AuthService.getAccessTokenBy()");

        return jwtTokenProvider.generateAccessTokenBy(refreshToken);
    }

    private boolean isValidateDuplicateMember(Member member){
        memberRepository.findByEmail(member.getEmail()).orElseThrow(MemberDuplicateException::new);

        return false;
    }
}
