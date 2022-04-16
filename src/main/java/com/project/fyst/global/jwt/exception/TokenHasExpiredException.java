package com.project.fyst.global.jwt.exception;

import com.project.fyst.global.exception.ForbiddenException;

public class TokenHasExpiredException extends ForbiddenException {
    public TokenHasExpiredException(){
        super("Token has Expired");
    }
}
