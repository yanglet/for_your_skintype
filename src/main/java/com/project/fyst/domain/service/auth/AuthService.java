package com.project.fyst.domain.service.auth;

import com.project.fyst.domain.dto.request.member.MemberLoginRequest;
import com.project.fyst.domain.dto.response.member.MemberLoginResponse;
import com.project.fyst.domain.entity.member.Member;
import com.project.fyst.domain.exception.MemberNotFoundException;
import com.project.fyst.domain.exception.PasswordMismatchException;
import com.project.fyst.domain.repository.member.MemberRepository;
import com.project.fyst.security.jwt.dto.AccessToken;
import com.project.fyst.security.jwt.service.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public MemberLoginResponse login(MemberLoginRequest loginRequest, HttpServletResponse response){
        Member member = memberRepository.findByEmail(loginRequest.getEmail()).
                orElseThrow(MemberNotFoundException::new);
        checkPassword(loginRequest.getPassword(), member.getPassword());
        AccessToken accessToken = jwtTokenProvider.generateAccessTokenBy(member);
        String refreshToken = jwtTokenProvider.generateRefreshToken(member);
        response.addHeader("AccessToken", accessToken.getToken());
        response.addHeader("RefreshToken", refreshToken);

        return new MemberLoginResponse(accessToken, refreshToken);
    }

    private void checkPassword(String loginPassword, String password) {
        if( !passwordEncoder.matches(loginPassword, password) ){
            throw new PasswordMismatchException();
        }
    }

    public AccessToken getAccessTokenBy(String refreshToken, HttpServletResponse response){
        response.setHeader("RefreshToken", refreshToken);
        return jwtTokenProvider.generateAccessTokenBy(refreshToken);
    }
}
