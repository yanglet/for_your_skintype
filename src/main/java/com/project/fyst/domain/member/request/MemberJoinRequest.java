package com.project.fyst.domain.member.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberJoinRequest {
    private Long id;
    @NotEmpty
    @Size(min = 2)
    private String name;
    private String gender;
    @NotEmpty
    private String email;
    @NotEmpty
    private String password;
    private String phoneNumber;
}
