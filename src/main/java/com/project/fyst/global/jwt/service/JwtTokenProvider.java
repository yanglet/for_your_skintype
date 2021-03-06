package com.project.fyst.global.jwt.service;

import com.project.fyst.domain.auth.exception.MemberNotFoundException;
import com.project.fyst.domain.member.entity.Member;
import com.project.fyst.domain.member.repository.MemberRepository;
import com.project.fyst.global.jwt.dto.AccessToken;
import com.project.fyst.global.jwt.exception.TokenIsInvalidException;
import com.project.fyst.global.security.PrincipalDetailsService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
@RequiredArgsConstructor
@Getter
public class JwtTokenProvider {

    private final PrincipalDetailsService principalDetailsService;
    private final MemberRepository memberRepository;

    @Value("${JWT.SECRET.KEY}")
    private String SECRET_KEY;

    @Value("${JWT.ACCESS.TOKEN.VALIDITY}")
    private Long JWT_ACCESS_TOKEN_VALIDITY;

    @Value("${JWT.REFRESH.TOKEN.VALIDITY}")
    private Long JWT_REFRESH_TOKEN_VALIDITY;

    @PostConstruct
    protected void init(){
        log.info("JwtTokenProvider.init()");
        SECRET_KEY = Base64.getEncoder().encodeToString(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
    }

    public AccessToken generateAccessToken(Map<String, Object> claimMap) {
        log.info("JwtTokenProvider.generateAccessToken()");
        Date expireTime = new Date(System.currentTimeMillis() + JWT_ACCESS_TOKEN_VALIDITY);
        String token = Jwts.builder()
                .setHeaderParam("type", "JWT")
                .setSubject("AccessToken")
                .setClaims(claimMap)
                .setIssuedAt(new Date())
                .setExpiration(expireTime)
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();

        return new AccessToken(token, expireTime.getTime());
    }

    public AccessToken generateAccessTokenBy(Member member){
        log.info("JwtTokenProvider.generateAccessTokenBy()");
        Map<String, Object> claims = new HashMap<>();
        claims.put("email", member.getEmail());
        return generateAccessToken(claims);
    }

    public AccessToken generateAccessTokenBy(String refreshToken){
        log.info("JwtTokenProvider.generateAccessTokenBy()");
        if( !isValidToken(refreshToken) ){
            throw new TokenIsInvalidException();
        }
        Member member = findMemberByToken(refreshToken);
        Map<String, Object> claims = new HashMap<>();
        claims.put("email", member.getEmail());
        return generateAccessToken(claims);
    }

    public String generateRefreshToken(Member member){
        log.info("JwtTokenProvider.generateRefreshToken()");
        Map<String, Object> claimMap = new HashMap<>();
        claimMap.put("email", member.getEmail());
        Date expireTime = new Date(System.currentTimeMillis() + JWT_REFRESH_TOKEN_VALIDITY);
        String token = Jwts.builder()
                .setHeaderParam("type", "JWT")
                .setSubject("RefreshToken")
                .setClaims(claimMap)
                .setIssuedAt(new Date())
                .setExpiration(expireTime)
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();

        return token;
    }

    public Long getAccessTokenExpiredTime(String token){
        Date expiration = Jwts
                .parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();

        return expiration.getTime();
    }

    public boolean isValidToken(String token){
        log.info("JwtTokenProvider.isValidToken()");
        Jws<Claims> claims;
        try{
            claims = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
            return !claims.getBody().getExpiration().before(new Date());
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    private String findEmailByToken(String token) {
        log.info("JwtTokenProvider.findEmailByToken()");
        Claims claims = Jwts
                .parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();

        return claims.get("email").toString();
    }

    public Member findMemberByToken(String token){
        log.info("JwtTokenProvider.findMemberByToken()");
        Claims claims = Jwts
                .parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();

        String email = claims.get("email").toString();

        return memberRepository.findByEmail(email).orElseThrow(MemberNotFoundException::new);
    }

    public Authentication getAuthentication(String token){
        log.info("JwtTokenProvider.getAuthentication()");
        UserDetails userDetails = principalDetailsService.loadUserByUsername(findEmailByToken(token));
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}
