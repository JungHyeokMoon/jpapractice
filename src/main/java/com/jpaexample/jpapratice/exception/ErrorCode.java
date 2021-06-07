package com.jpaexample.jpapratice.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    INVALID_PASSWORD("패스워드 형식이 올바르지 않습니다.",HttpStatus.BAD_REQUEST),
    DUPLICATE_USER("이미 존재하는 회원입니다.", HttpStatus.INTERNAL_SERVER_ERROR),
    MISSING_REQUIRED_ITEMS("필수항목이 존재하지 않습니다.", HttpStatus.BAD_REQUEST),
    SELECT_EMPTY("데이터베이스 조회결과가 존재하지 않습니다.", HttpStatus.INTERNAL_SERVER_ERROR),
    ALREADY_EXIST_PASSWORD("이미 사용했던 패스워드입니다.", HttpStatus.BAD_REQUEST),

    ;
    private final String message;
    private final HttpStatus httpStatus;

    ErrorCode(String message, HttpStatus httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
    }
}
