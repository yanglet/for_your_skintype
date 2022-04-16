package com.project.fyst.domain.auth.service;

import com.project.fyst.domain.member.request.MemberLoginRequest;
import com.project.fyst.domain.member.response.MemberLoginResponse;
import com.project.fyst.domain.member.entity.Member;
import com.project.fyst.domain.auth.exception.MemberDuplicateException;
import com.project.fyst.domain.auth.exception.MemberNotFoundException;
import com.project.fyst.domain.auth.exception.PasswordMismatchException;
import com.project.fyst.domain.member.repository.MemberRepository;
import com.project.fyst.global.jwt.dto.AccessToken;
import com.project.fyst.global.jwt.service.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public MemberLoginResponse login(MemberLoginRequest loginRequest, HttpServletResponse response){
        log.info("AuthService.login()");
        Member member = memberRepository.findByEmail(loginRequest.getEmail()).
                orElseThrow(MemberNotFoundException::new);
        checkPassword(loginRequest.getPassword(), member.getPassword());
        AccessToken accessToken = jwtTokenProvider.generateAccessTokenBy(member);
        String refreshToken = jwtTokenProvider.generateRefreshToken(member);
        response.addHeader("AccessToken", accessToken.getToken());
        response.addHeader("RefreshToken", refreshToken);

        return new MemberLoginResponse(accessToken, refreshToken);
    }

    public Member join(Member member){
        isValidateDuplicateMember(member);
        return memberRepository.save(member);
    }

    private void checkPassword(String loginPassword, String password) {
        log.info("AuthService.checkPassword()");
        if( !passwordEncoder.matches(loginPassword, password) ){
            throw new PasswordMismatchException();
        }
    }

    public AccessToken getAccessTokenBy(String refreshToken, HttpServletResponse response){
        log.info("AuthService.getAccessTokenBy()");
        response.setHeader("RefreshToken", refreshToken);
        return jwtTokenProvider.generateAccessTokenBy(refreshToken);
    }

    private boolean isValidateDuplicateMember(Member member){
        memberRepository.findByEmail(member.getEmail()).orElseThrow(MemberDuplicateException::new);

        return false;
    }
}
