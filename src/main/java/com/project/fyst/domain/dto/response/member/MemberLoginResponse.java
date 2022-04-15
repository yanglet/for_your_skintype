package com.project.fyst.domain.dto.response.member;

import com.project.fyst.security.jwt.dto.AccessToken;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MemberLoginResponse {
    private AccessToken accessToken;
    private String refreshToken;
}
