package com.project.fyst.security.jwt.service;

import com.project.fyst.security.auth.PrincipalDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Service
@Slf4j
@RequiredArgsConstructor
public class JwtService {

    private final PrincipalDetailsService principalDetailsService;

    @Value("${JWT.SECRET.KEY}")
    private String SECRET_KEY;

    @Value("${JWT.ACCESS.TOKEN.VALIDITY}")
    private Long JWT_ACCESS_TOKEN_VALIDITY;

    @Value("${JWT.REFRESH.TOKEN.VALIDITY}")
    private Long JWT_REFRESH_TOKEN_VALIDITY;

    @PostConstruct
    protected void init(){
        SECRET_KEY = Base64.getEncoder().encodeToString(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
    }


}
