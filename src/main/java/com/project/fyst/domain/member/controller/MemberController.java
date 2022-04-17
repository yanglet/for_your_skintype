package com.project.fyst.domain.member.controller;

import com.project.fyst.domain.auth.exception.MemberNotFoundException;
import com.project.fyst.domain.common.dto.Result;
import com.project.fyst.domain.member.dto.MemberDto;
import com.project.fyst.domain.member.dto.MemberWithLikedItemDto;
import com.project.fyst.domain.member.entity.Member;
import com.project.fyst.domain.member.repository.MemberRepository;
import com.project.fyst.domain.member.response.MemberMyPageResponse;
import com.project.fyst.global.jwt.dto.AccessToken;
import com.project.fyst.global.jwt.service.JwtTokenProvider;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
@RequestMapping("v1/api/members")
@Slf4j
public class MemberController {

    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;

    // 권한 처리 필요
    @ApiOperation("전체 회원 정보 조회")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Result getMembers(@RequestParam(required = false, defaultValue = "n") String detail) {
        if( detail.equals("n") ){ // 장바구니 미포함
            List<MemberDto> collect = memberRepository.findAll()
                    .stream()
                    .map(MemberDto::new)
                    .collect(Collectors.toList());

            return new Result(collect);
        }
        else{ // 장바구니 포함
            List<MemberWithLikedItemDto> collect = memberRepository.findAll()
                    .stream()
                    .map(MemberWithLikedItemDto::new)
                    .collect(Collectors.toList());

            return new Result(collect);
        }
    }

    // 권한 처리 필요
    @ApiOperation("회원 상세 정보 조회")
    @GetMapping("/{memberId}")
    @ResponseStatus(HttpStatus.OK)
    public Result getMember(@PathVariable("memberId") Long memberId) {
        return new Result(new MemberMyPageResponse(memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new)));
    }

    @ApiOperation("회원 삭제")
    @DeleteMapping("/{memberId}")
    @ResponseStatus(HttpStatus.NON_AUTHORITATIVE_INFORMATION)
    public void deleteMember(@PathVariable("memberId") Long memberId){
        Member member = memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);

        memberRepository.delete(member);
    }

}
