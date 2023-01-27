package com.dq.aquaranth.menu.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * API 공통 예외 클래스입니다.
 *
 * @author 김민준
 */
@Getter
@RequiredArgsConstructor
public class CommonException extends RuntimeException{
    private final ErrorCodes errorCodes;
}
