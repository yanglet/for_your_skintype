package com.project.fyst.domain.exception;

public class MemberDuplicateException extends IllegalArgumentException{
    public MemberDuplicateException() {
        super("중복된 회원입니다.");
    }
}
