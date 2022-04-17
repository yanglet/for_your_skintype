package com.project.fyst.domain.auth.controller;

import com.project.fyst.domain.member.request.MemberJoinRequest;
import com.project.fyst.domain.member.request.MemberLoginRequest;
import com.project.fyst.domain.member.response.MemberJoinResponse;
import com.project.fyst.domain.member.response.MemberLoginResponse;
import com.project.fyst.domain.member.dto.MemberDto;
import com.project.fyst.domain.member.entity.Member;
import com.project.fyst.domain.auth.service.AuthService;
import com.project.fyst.global.jwt.dto.AccessToken;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.BindingResult;
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

    @ApiOperation("로그인")
    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public MemberLoginResponse login(@RequestBody MemberLoginRequest memberLoginRequest,
                                     HttpServletResponse response){
        log.info("AuthController.login()");
        return authService.login(memberLoginRequest, response);
    }

    @ApiOperation("회원가입")
    @PostMapping("/join")
    @ResponseStatus(HttpStatus.CREATED)
    public MemberJoinResponse join(@Validated @RequestBody MemberJoinRequest memberJoinRequest,
                                   BindingResult bindingResult){
        Member member = Member.builder()
                .name(memberJoinRequest.getName())
                .gender(memberJoinRequest.getGender())
                .email(memberJoinRequest.getEmail())
                .password(memberJoinRequest.getPassword())
                .phoneNumber(memberJoinRequest.getPhoneNumber())
                .roles("ROLE_USER")
                .build();

        member = authService.join(member);

        return new MemberJoinResponse(new MemberDto(member));
    }

    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @ApiOperation("엑세스 토큰 재발급")
    @GetMapping("/accesstoken")
    @ResponseStatus(HttpStatus.CREATED)
    public AccessToken requestAccessToken(@RequestHeader String refreshToken,
                                          HttpServletResponse response){
        log.info("AuthController.requestAccessToken()");
        return authService.getAccessTokenBy(refreshToken, response);
    }

}
