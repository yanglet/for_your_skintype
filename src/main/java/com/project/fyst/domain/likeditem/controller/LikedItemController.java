package com.project.fyst.domain.likeditem.controller;

import com.project.fyst.domain.auth.exception.MemberNotFoundException;
import com.project.fyst.domain.common.dto.Result;
import com.project.fyst.domain.item.entity.Item;
import com.project.fyst.domain.item.repository.ItemRepository;
import com.project.fyst.domain.likeditem.dto.LikedItemDto;
import com.project.fyst.domain.likeditem.repository.LikedItemRepository;
import com.project.fyst.domain.member.entity.Member;
import com.project.fyst.domain.member.repository.MemberRepository;
import com.project.fyst.global.exception.NotFoundException;
import com.project.fyst.global.jwt.dto.AccessToken;
import com.project.fyst.global.jwt.service.JwtTokenProvider;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
@RequestMapping("v1/api/likeditems")
@Slf4j
public class LikedItemController {

    private final LikedItemRepository likedItemRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @ApiOperation("상품을 장바구니에 추가")
    @GetMapping("/{itemId}")
    @ResponseStatus(HttpStatus.CREATED)
    public LikedItemDto add(@RequestHeader("AccessToken") AccessToken accessToken,
                     @PathVariable("itemId") Long itemId){
        // lazyinitializationexception -> 페치조인으로 해결
        Member member = memberRepository.findOneByEmailWithLikedItems(jwtTokenProvider
                .findMemberByToken(accessToken.getAccessToken())
                .getEmail());
        Item item = itemRepository.findById(itemId).orElseThrow(NotFoundException::new);

        return new LikedItemDto(likedItemRepository.save(item, member));
    }

    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @ApiOperation("회원에 따른 장바구니 조회")
    @GetMapping("/{memberId}/likeditems")
    @ResponseStatus(HttpStatus.OK)
    public Result getLikedItemsByMember(@PathVariable("memberId") Long memberId){
        List<LikedItemDto> collect = likedItemRepository.findAllByEmail(memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new).getEmail())
                .stream()
                .map(LikedItemDto::new)
                .collect(Collectors.toList());

        return new Result(collect);
    }

    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @ApiOperation("장바구니 취소")
    @DeleteMapping("/{likedItemId}")
    @ResponseStatus(HttpStatus.NON_AUTHORITATIVE_INFORMATION)
    public void deleteLikedItem(@PathVariable("likedItemId") Long likedItemId){
        likedItemRepository.delete(likedItemId);
    }

}
