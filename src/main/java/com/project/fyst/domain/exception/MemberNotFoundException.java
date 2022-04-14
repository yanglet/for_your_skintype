package com.project.fyst.domain.exception;

import com.project.fyst.security.exception.NotFoundException;

public class MemberNotFoundException extends NotFoundException {
    public MemberNotFoundException() {
        super("PassWord is Incorrect");
    }
}
