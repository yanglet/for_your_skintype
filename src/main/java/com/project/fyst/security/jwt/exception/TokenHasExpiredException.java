package com.project.fyst.security.jwt.exception;

import com.project.fyst.security.exception.ForbiddenException;

public class TokenHasExpiredException extends ForbiddenException {

    public TokenHasExpiredException(){
        super("Token has Expired");
    }
}
