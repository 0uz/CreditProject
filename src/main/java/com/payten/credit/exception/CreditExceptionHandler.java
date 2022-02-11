package com.payten.credit.exception;


import com.payten.credit.controller.common.ExceptionResponse;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class CreditExceptionHandler {

    @ExceptionHandler(DataNotFoundException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ExceptionResponse handleDataNotFoundException(DataNotFoundException e){
        return new ExceptionResponse(e.getExceptionType(), e.getDetails());
    }

    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse handleValidationException(ValidationException e){
        return new ExceptionResponse(e.getExceptionType(), List.of(e.getDetail()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse handleJavaxValidationException(MethodArgumentNotValidException e){
        //Collecting all exceptions
        List<String> messages = new ArrayList<>(e.getBindingResult().getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList());
        return new ExceptionResponse(ExceptionType.VALIDATION_EXCEPTION,messages);
    }

    @ExceptionHandler()
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionResponse handleException(Exception e) {
        List<String> messages = new ArrayList<>();
        messages.add(e.getMessage());
        return new ExceptionResponse(ExceptionType.GENERIC_EXCEPTION, messages);
    }


}
