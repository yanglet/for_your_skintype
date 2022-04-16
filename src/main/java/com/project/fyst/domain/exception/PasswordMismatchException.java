package com.project.fyst.domain.exception;

import com.project.fyst.security.exception.ForbiddenException;

public class PasswordMismatchException extends ForbiddenException {
    public PasswordMismatchException() {
        super("비밀번호가 일치하지 않습니다.");
    }
}
