package com.project.fyst.domain.auth.exception;

import com.project.fyst.global.exception.ForbiddenException;

public class PasswordMismatchException extends ForbiddenException {
    public PasswordMismatchException() {
        super("비밀번호가 일치하지 않습니다.");
    }
}
