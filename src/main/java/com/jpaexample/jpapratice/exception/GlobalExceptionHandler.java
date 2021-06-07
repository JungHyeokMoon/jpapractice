package com.jpaexample.jpapratice.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.NoSuchElementException;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(NoSuchElementException.class)
    protected ResponseEntity<ErrorResponse> handleNoSuchElementException(NoSuchElementException e) {
        log.error("NoSuchElementException", e);
        final ErrorResponse errorResponse = ErrorResponse.of(ErrorCode.SELECT_EMPTY);
        return new ResponseEntity<>(errorResponse, errorResponse.getHttpStatus());
    }
}
