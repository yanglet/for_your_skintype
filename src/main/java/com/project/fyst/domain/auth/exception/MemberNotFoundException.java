package com.project.fyst.domain.auth.exception;

import com.project.fyst.global.exception.NotFoundException;

public class MemberNotFoundException extends NotFoundException {
    public MemberNotFoundException() {
        super("회원을 찾을 수 없습니다.");
    }
}
