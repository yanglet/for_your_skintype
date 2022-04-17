package com.project.fyst.global.error;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class ErrorResult<T> {
    private String statusCode;
    private String message;
    private T errors;

    public ErrorResult(String statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }
}
