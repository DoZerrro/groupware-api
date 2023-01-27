package com.dq.aquaranth.menu.exception;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

/**
 * 공통 예외 상수입니다.
 *
 * @author 김민준
 */
@Getter
@RequiredArgsConstructor
public enum ErrorCodes {
    MENU_NOT_FOUND(NOT_FOUND, "메뉴를 찾을 수 없습니다.",100L),
    UNAUTHORIZED_MEMBER(UNAUTHORIZED, "메뉴에 대한 접근권한이 없습니다.",101L),
    INVALID_USER(UNAUTHORIZED, "아이디 또는 비밀번호를 확인해주세요.",200L),
    TOKEN_MISSING(UNAUTHORIZED, ErrorCodes.SESSION_EXPIRED, 300L),
    ACCESS_TOKEN_EXPIRED(UNAUTHORIZED, ErrorCodes.SESSION_EXPIRED,301L),
    REFRESH_TOKEN_EXPIRED(UNAUTHORIZED, ErrorCodes.SESSION_EXPIRED,302L),
    REDIS_USER_NOT_FOUND(BAD_REQUEST, ErrorCodes.SESSION_EXPIRED,400L),
    FOREIGN_KEY_ERROR(BAD_REQUEST, "삭제할 수 없는 메뉴입니다.",500L),
    // TODO : 클라이언트에게 내부 에러를 공개해야 하는가?
    ERROR(INTERNAL_SERVER_ERROR, "에러가 발생했습니다.",900L);

    private static final String SESSION_EXPIRED = "세션이 만료되었습니다. 다시 로그인해 주세요.";
    private final HttpStatus httpStatus;
    private final String detail;
    private final Long detailErrorCode;
}
