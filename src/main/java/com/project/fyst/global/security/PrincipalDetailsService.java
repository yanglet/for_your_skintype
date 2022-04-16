package com.project.fyst.global.security;

import com.project.fyst.domain.member.entity.Member;
import com.project.fyst.domain.auth.exception.MemberNotFoundException;
import com.project.fyst.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PrincipalDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("PrincipalDetailsService.loadUserByUsername()");
        Member member = memberRepository.findByEmail(username).orElseThrow(MemberNotFoundException::new);
        return new PrincipalDetails(member);
    }
}
