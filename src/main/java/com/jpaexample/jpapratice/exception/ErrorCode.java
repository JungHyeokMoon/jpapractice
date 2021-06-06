package com.jpaexample.jpapratice.exception;

import org.springframework.http.HttpStatus;

public enum ErrorCode {
    INVALID_PASSWORD("패스워드 형식이 올바르지 않습니다.",HttpStatus.BAD_REQUEST),
    DUPLICATE_USER("이미 존재하는 회원입니다.", HttpStatus.INTERNAL_SERVER_ERROR),
    MISSING_REQUIRED_ITEMS("필수항목이 존재하지 않습니다.", HttpStatus.BAD_REQUEST),
    ;
    private final String message;
    private final HttpStatus status;

    ErrorCode(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }
}
