package com.jpaexample.jpapratice.exception;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ErrorResponse {

    private String message;
    private HttpStatus httpStatus;

    private ErrorResponse(final ErrorCode errorCode) {
        this.message = errorCode.getMessage();
        this.httpStatus = errorCode.getHttpStatus();
    }

    public static ErrorResponse of(final ErrorCode errorCode){
        return new ErrorResponse(errorCode);
    }
}
