package com.jpaexample.jpapratice.exception;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException{

    private final ErrorCode errorCode;

    public BusinessException(ErrorCode errorCode){
        super();
        this.errorCode = errorCode;
    }

    public BusinessException(String message, Throwable cause, ErrorCode errorcode){
        super(message,cause);
        this.errorCode = errorcode;
    }

    public BusinessException(String message,ErrorCode errorCode){
        super(message);
        this.errorCode = errorCode;
    }

    public BusinessException(Throwable cause,ErrorCode errorCode){
        super(cause);
        this.errorCode = errorCode;
    }
}
