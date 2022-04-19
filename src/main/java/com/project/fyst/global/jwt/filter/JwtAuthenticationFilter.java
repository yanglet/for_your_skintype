package com.project.fyst.global.jwt.filter;

import com.project.fyst.global.jwt.service.JwtTokenProvider;
import com.project.fyst.global.redis.repository.LogoutAccessTokenRedisRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends GenericFilterBean {

    private final JwtTokenProvider jwtTokenProvider;
    private final LogoutAccessTokenRedisRepository logoutAccessTokenRedisRepository;

    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        log.info("JwtAuthenticationFilter.doFilter");
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String accessToken = httpRequest.getHeader("AccessToken");

        if( accessToken != null ){
            checkLogout(accessToken);
            if( jwtTokenProvider.isValidToken(accessToken) ){
                Authentication auth = jwtTokenProvider.getAuthentication(accessToken);
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }

        chain.doFilter(request, response);
    }

    private void checkLogout(String accessToken) {
        if (logoutAccessTokenRedisRepository.existsById(accessToken)) {
            throw new IllegalArgumentException("로그아웃된 회원입니다.");
        }
    }
}
