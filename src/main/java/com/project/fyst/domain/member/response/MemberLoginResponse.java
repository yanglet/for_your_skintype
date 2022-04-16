package com.project.fyst.domain.member.response;

import com.project.fyst.global.jwt.dto.AccessToken;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MemberLoginResponse {
    private AccessToken accessToken;
    private String refreshToken;
}
