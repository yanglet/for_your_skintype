package com.project.fyst.global.jwt.exception;

import com.project.fyst.global.exception.ForbiddenException;

public class TokenIsInvalidException extends ForbiddenException {
    public TokenIsInvalidException() {
    super("Token is invalid");
}
}
