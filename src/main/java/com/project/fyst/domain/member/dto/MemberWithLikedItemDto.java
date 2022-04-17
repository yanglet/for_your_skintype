package com.project.fyst.domain.member.dto;

import com.project.fyst.domain.likeditem.dto.LikedItemDto;
import com.project.fyst.domain.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberWithLikedItemDto {
    private Long id;
    private String name;
    private String gender;
    private String email;
    private String password;
    private String phoneNumber;
    private List<LikedItemDto> likedItems = new ArrayList<>();

    public MemberWithLikedItemDto(Member member) {
        this.id = member.getId();
        this.name = member.getName();
        this.gender = member.getGender();
        this.email = member.getEmail();
        this.password = member.getPassword();
        this.phoneNumber = member.getPhoneNumber();
        this.likedItems = member.getLikedItems()
                .stream()
                .map(l -> new LikedItemDto(l))
                .collect(Collectors.toList());
    }
}
