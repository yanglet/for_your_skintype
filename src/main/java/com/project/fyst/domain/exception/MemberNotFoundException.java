package com.project.fyst.domain.exception;

import com.project.fyst.security.exception.NotFoundException;

public class MemberNotFoundException extends NotFoundException {
    public MemberNotFoundException() {
        super("회원을 찾을 수 없습니다.");
    }
}
