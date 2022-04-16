package com.project.fyst.domain.dto.simple;

import com.project.fyst.domain.entity.member.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class MemberDto {
    private String name;
    private String gender;
    private String email;
    private String password;
    private String phoneNumber;
    private String roles;

    public static MemberDto of(Member member){
        return MemberDto.builder()
                .name(member.getName())
                .gender(member.getGender())
                .email(member.getEmail())
                .password(member.getPassword())
                .phoneNumber(member.getPhoneNumber())
                .roles(member.getRoles())
                .build();
    }
}
