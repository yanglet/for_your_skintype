package com.project.fyst.domain.item.controller;

import com.project.fyst.domain.common.dto.Result;
import com.project.fyst.domain.item.dto.ItemDto;
import com.project.fyst.domain.item.entity.SkinType;
import com.project.fyst.domain.item.repository.ItemRepository;
import com.project.fyst.global.jwt.dto.AccessToken;
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
@RequestMapping("v1/api/items")
@Slf4j
public class ItemController {

    private final ItemRepository itemRepository;

    @ApiOperation("전체 상품 조회")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Result getItems(){
        List<ItemDto> collect = itemRepository.findAll()
                .stream()
                .map(ItemDto::new)
                .collect(Collectors.toList());

        return new Result(collect);
    }

    @Secured("ROLE_ADMIN")
    @ApiOperation("상품 삭제")
    @DeleteMapping("/{itemId}")
    @ResponseStatus(HttpStatus.NON_AUTHORITATIVE_INFORMATION)
    public void deleteItem(@RequestHeader("AccessToken") AccessToken accessToken,
                           @PathVariable("itemId") Long itemId){
        itemRepository.delete(itemId, accessToken);
    }

    @ApiOperation("브랜드에 따른 상품 조회")
    @GetMapping("/brand")
    @ResponseStatus(HttpStatus.OK)
    public Result getItemsByBrand(@RequestParam("name") String brand){
        List<ItemDto> collect = itemRepository.findItemsByBrand(brand)
                .stream()
                .map(ItemDto::new)
                .collect(Collectors.toList());

        return new Result(collect);
    }

    @ApiOperation("피부타입에 따른 상품 조회")
    @GetMapping("/skintype")
    @ResponseStatus(HttpStatus.OK)
    public Result getItemsBySkinType(@RequestParam("skintype") String skinType){
        List<ItemDto> collect = itemRepository.findItemsBySkinType(SkinType.valueOf(skinType))
                .stream()
                .map(ItemDto::new)
                .collect(Collectors.toList());

        return new Result(collect);
    }
}
