package com.project.fyst.global.error;

import com.project.fyst.domain.auth.exception.MemberDuplicateException;
import com.project.fyst.domain.auth.exception.MemberNotFoundException;
import com.project.fyst.domain.auth.exception.PasswordMismatchException;
import com.project.fyst.global.exception.ForbiddenException;
import com.project.fyst.global.exception.NotFoundException;
import com.project.fyst.global.jwt.exception.TokenHasExpiredException;
import com.project.fyst.global.jwt.exception.TokenIsInvalidException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice("com.project.fyst.domain")
@Slf4j
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    protected ErrorResult handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("handleMethodArgumentNotValidException", e);
        return new ErrorResult("400", "검증 오류!", e.getBindingResult());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    protected ErrorResult handleMemberDuplicateException(MemberDuplicateException e){
        log.error("handleMemberDuplicateException", e);
        return new ErrorResult("400", e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    protected ErrorResult handleMemberNotFoundException(MemberNotFoundException e){
        log.error("handleMemberNotFoundException", e);
        return new ErrorResult("400", e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    protected ErrorResult MethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e){
        log.error("MethodArgumentTypeMismatchException", e);
        return new ErrorResult("400", "Enum 타입 바인딩 오류!");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    protected ErrorResult handlePasswordMismatchException(PasswordMismatchException e){
        log.error("handlePasswordMismatchException", e);
        return new ErrorResult("400", e.getMessage());
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler
    protected ErrorResult handleForbiddenException(ForbiddenException e){
        log.error("handleForbiddenException", e);
        return new ErrorResult("403", e.getMessage());
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler
    protected ErrorResult handleTokenHasExpiredException(TokenHasExpiredException e){
        log.error("handleTokenHasExpiredException", e);
        return new ErrorResult("403", "토큰이 만료되었습니다.");
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler
    protected ErrorResult handleTokenIsInvalidException(TokenIsInvalidException e){
        log.error("handleTokenIsInvalidException", e);
        return new ErrorResult("403", "토큰이 유효하지 않습니다.");
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler
    protected ErrorResult handleNotFoundException(NotFoundException e){
        log.error("handleForbiddenException", e);
        return new ErrorResult("404", e.getMessage());
    }

    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler
    protected ErrorResult handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e){
        log.error("handleHttpRequestMethodNotSupportedException", e);
        return new ErrorResult("405", "지원하지않는 Http Method 입니다. ");
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler
    protected ErrorResult handleException(Exception e){
        log.error("handleException", e);
        return new ErrorResult("500", "서버 오류!");
    }

}
