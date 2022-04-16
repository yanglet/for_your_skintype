package com.project.fyst.global.jwt.exception;

import com.project.fyst.global.exception.ForbiddenException;

public class TokenInsInvalidException extends ForbiddenException {
    public TokenInsInvalidException() {
    super("Token is invalid");
}
}
