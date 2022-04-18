package com.project.fyst.domain.member.dto;

import com.project.fyst.domain.member.entity.Member;
import lombok.Data;

@Data
public class MemberDto {
    private Long id;
    private String name;
    private String gender;
    private String email;
    private String password;
    private String phoneNumber;
    private String roles;

    public MemberDto(Member member) {
        this.id = member.getId();
        this.name = member.getName();
        this.gender = member.getGender();
        this.email = member.getEmail();
        this.password = member.getPassword();
        this.phoneNumber = member.getPhoneNumber();
        this.roles = member.getRoles();
    }

}
