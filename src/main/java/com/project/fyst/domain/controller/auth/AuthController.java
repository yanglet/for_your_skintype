package com.project.fyst.domain.controller.auth;

import com.project.fyst.domain.dto.request.member.MemberJoinRequest;
import com.project.fyst.domain.dto.request.member.MemberLoginRequest;
import com.project.fyst.domain.dto.response.member.MemberJoinResponse;
import com.project.fyst.domain.dto.response.member.MemberLoginResponse;
import com.project.fyst.domain.dto.simple.MemberDto;
import com.project.fyst.domain.entity.member.Member;
import com.project.fyst.domain.service.auth.AuthService;
import com.project.fyst.security.jwt.dto.AccessToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
@RequestMapping("v1/api/auth")
@Slf4j
public class AuthController {
    private final AuthService authService;

    // 로그인
    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public MemberLoginResponse login(@RequestBody MemberLoginRequest memberLoginRequest,
                                     HttpServletResponse response){
        log.info("AuthController.login()");
        return authService.login(memberLoginRequest, response);
    }

    // 회원가입
    @PostMapping("/join")
    @ResponseStatus(HttpStatus.CREATED)
    public MemberJoinResponse join(@Validated @RequestBody MemberJoinRequest memberJoinRequest){
        Member member = Member.builder()
                .name(memberJoinRequest.getName())
                .gender(memberJoinRequest.getGender())
                .email(memberJoinRequest.getEmail())
                .password(memberJoinRequest.getPassword())
                .phoneNumber(memberJoinRequest.getPhoneNumber())
                .roles("ROLE_USER")
                .build();

        member = authService.join(member);

        return new MemberJoinResponse(MemberDto.of(member));
    }

    // 엑세스 토큰 재발급
    @GetMapping("/accesstoken")
    @ResponseStatus(HttpStatus.CREATED)
    public AccessToken requestAccessToken(@RequestHeader String refreshToken,
                                          HttpServletResponse response){
        log.info("AuthController.requestAccessToken()");
        return authService.getAccessTokenBy(refreshToken, response);
    }

}
