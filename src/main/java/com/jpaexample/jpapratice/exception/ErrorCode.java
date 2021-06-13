package com.jpaexample.jpapratice.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    /* 400 BAD_REQUEST: 잘못된 요청 */
    INVALID_PASSWORD("패스워드 형식이 올바르지 않습니다.", BAD_REQUEST),
    MISSING_REQUIRED_ITEMS("필수항목이 존재하지 않습니다.", BAD_REQUEST),
    QUANTITY_LACK("수량이 모자랍니다", BAD_REQUEST),
    SAVE_NOT_ALLOW_NULL("Repository Save시 Null이 허용되지 않습니다.", BAD_REQUEST),
    ALREADY_COMP("이미 배송완료된 상품은 취소가 불가능합니다.", BAD_REQUEST),

    /* 404 NOT_FOUND: Resource를 찾을 수 없음 */
    SELECT_EMPTY("데이터베이스 조회결과가 존재하지 않습니다.", NOT_FOUND),

    /* 409 CONFLICT : Resource의 현재 상태와 충돌. 보통 중복된 데이터존재 */
    DUPLICATE_USER("이미 존재하는 회원입니다.", CONFLICT),
    ALREADY_EXIST_PASSWORD("이미 사용했던 패스워드입니다.", CONFLICT),

    SERVER_ERROR("내부서비스 에러입니다.", INTERNAL_SERVER_ERROR),
    ;
    private final String message;
    private final HttpStatus httpStatus;

}
