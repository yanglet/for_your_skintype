package com.project.fyst.domain.member.response;

import com.project.fyst.domain.member.entity.Member;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MemberMyPageResponse {
    private Long id;
    private String name;
    private String gender;
    private String email;
    private String phoneNumber;

    public MemberMyPageResponse(Member member) {
        this.id = member.getId();
        this.name = member.getName();
        this.gender = member.getGender();
        this.email = member.getEmail();
        this.phoneNumber = member.getPhoneNumber();
    }
}
