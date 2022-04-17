package com.project.fyst.domain.likeditem.controller;

import com.project.fyst.domain.common.dto.Result;
import com.project.fyst.domain.item.entity.Item;
import com.project.fyst.domain.likeditem.dto.LikedItemDto;
import com.project.fyst.domain.likeditem.entity.LikedItem;
import com.project.fyst.domain.likeditem.repository.LikedItemRepository;
import com.project.fyst.domain.likeditem.request.LikedItemSaveRequest;
import com.project.fyst.domain.member.entity.Member;
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
    private final JwtTokenProvider jwtTokenProvider;

    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @ApiOperation("상품을 장바구니에 추가")
    @PostMapping("/{itemId}")
    @ResponseStatus(HttpStatus.CREATED)
    public LikedItemDto add(@RequestHeader("AccessToken") AccessToken accessToken,
                     @PathVariable("itemId") Long itemId,
                     @RequestBody LikedItemSaveRequest likedItemSaveRequest){
        Member member = jwtTokenProvider.findMemberByToken(accessToken.getToken());
        Item item = likedItemSaveRequest.getItem();

        return new LikedItemDto(likedItemRepository.save(LikedItem.of(item, member)));
    }

    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @ApiOperation("회원에 따른 장바구니 조회")
    @GetMapping("/{memberId}/likeditems")
    @ResponseStatus(HttpStatus.OK)
    public Result getLikedItemsByMember(@RequestHeader("AccessToken") AccessToken accessToken,
                                  @PathVariable("memberId") Long memberId){
        List<LikedItemDto> collect = likedItemRepository.findAllByEmail(jwtTokenProvider.findMemberByToken(accessToken.getToken()).getEmail())
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
