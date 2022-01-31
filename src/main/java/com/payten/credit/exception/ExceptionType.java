package com.payten.credit.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ExceptionType {
    GENERIC_EXCEPTION(1, "An unknown error occurred"),
    VALIDATION_EXCEPTION(2, "Validation error occurred"),
    USER_DATA_NOT_FOUND(1001,"User didn't find");

    private final Integer code;
    private final String message;
}
