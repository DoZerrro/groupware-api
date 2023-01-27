package com.dq.aquaranth.menu.aop;

import com.dq.aquaranth.login.controller.UserControllerMarker;
import com.dq.aquaranth.menu.dto.response.ErrorResponseDTO;
import com.dq.aquaranth.menu.exception.CommonException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 유저 컨트롤러에 발생하는 모든 에러를 처리합니다.
 *
 * @author 김민준
 */
@Log4j2
@RestControllerAdvice(basePackageClasses = UserControllerMarker.class)
public class UserControllerAdvice {

    @ExceptionHandler({CommonException.class})
    protected ResponseEntity<ErrorResponseDTO> handleMenuException(CommonException commonException) {
        log.error("토큰 에러 : {}", commonException.getErrorCodes());
        return ErrorResponseDTO.toResponseEntity(commonException.getErrorCodes());
    }
}


