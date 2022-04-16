package com.project.fyst.domain.controller.auth;

import com.project.fyst.security.jwt.dto.AccessToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/api/test")
@Slf4j
public class TestController {

    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @GetMapping("/user")
    public String testUser(){
        return "user!";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/admin")
    public String testAdmin(){
        return "admin!";
    }
}
