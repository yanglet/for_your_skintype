package com.project.fyst.domain.exception;

import com.project.fyst.security.exception.ForbiddenException;

public class PasswordMismatchException extends ForbiddenException {
    public PasswordMismatchException() {
        super("PassWord is Incorrect");
    }
}
