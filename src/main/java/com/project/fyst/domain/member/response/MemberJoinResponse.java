package com.project.fyst.domain.member.response;

import com.project.fyst.domain.member.dto.MemberDto;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MemberJoinResponse {
    private MemberDto memberDto;
}
