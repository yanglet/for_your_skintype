package com.project.fyst.domain.controller;

import com.project.fyst.domain.dto.request.member.MemberLoginRequest;
import com.project.fyst.domain.dto.response.member.MemberLoginResponse;
import com.project.fyst.domain.service.auth.AuthService;
import com.project.fyst.security.jwt.dto.AccessToken;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
@RequestMapping("v1/api/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public MemberLoginResponse login(@RequestBody MemberLoginRequest memberLoginRequest,
                                     HttpServletResponse response){

        return authService.login(memberLoginRequest, response);
    }

    // 엑세스 토큰 재발급
    @GetMapping("/accesstoken")
    @ResponseStatus(HttpStatus.CREATED)
    public AccessToken requestAccessToken(@RequestHeader String refreshToken,
                                          HttpServletResponse response){
        return authService.getAccessTokenBy(refreshToken, response);
    }

}
