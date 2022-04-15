package com.project.fyst.security.jwt.exception;

import com.project.fyst.security.exception.ForbiddenException;

public class TokenInsInvalidException extends ForbiddenException {
    public TokenInsInvalidException() {
    super("Token is invalid");
}
}
