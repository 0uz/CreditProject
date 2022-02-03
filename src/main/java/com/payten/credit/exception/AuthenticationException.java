package com.payten.credit.exception;

import lombok.Getter;

import java.util.List;

@Getter
public class AuthenticationException extends RuntimeException{

    private final ExceptionType exceptionType;
    private List<String> details;

    public AuthenticationException(ExceptionType exceptionType, List<String> details) {
        super(exceptionType.getMessage());
        this.exceptionType = exceptionType;
        this.details = details;
    }

    public AuthenticationException(ExceptionType exceptionType) {
        super(exceptionType.getMessage());
        this.exceptionType = exceptionType;
    }
}
