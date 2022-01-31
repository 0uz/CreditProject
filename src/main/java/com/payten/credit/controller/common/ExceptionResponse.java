package com.payten.credit.controller.common;


import com.payten.credit.exception.ExceptionType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ExceptionResponse {
    private Integer code;
    private String message;
    private List<String> detail;

    public ExceptionResponse(ExceptionType exceptionType, List<String> detail) {
        this.code = exceptionType.getCode();
        this.message = exceptionType.getMessage();
        this.detail = detail;
    }

    public ExceptionResponse(ExceptionType exceptionType) {
        this.code = exceptionType.getCode();
        this.message = exceptionType.getMessage();
    }
}
