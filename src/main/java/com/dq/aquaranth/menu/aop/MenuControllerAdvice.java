package com.dq.aquaranth.menu.aop;

import com.dq.aquaranth.menu.controller.MenuControllerMarker;
import com.dq.aquaranth.menu.dto.response.ErrorResponseDTO;
import com.dq.aquaranth.menu.exception.CommonException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 메뉴 컨트롤러에 발생하는 모든 에러를 처리합니다.
 * @author 김민준
 */
@Log4j2
@RestControllerAdvice(basePackageClasses = MenuControllerMarker.class)
public class MenuControllerAdvice {

    @ExceptionHandler({CommonException.class})
    protected ResponseEntity<ErrorResponseDTO> handleMenuException(CommonException commonException) {
        log.error("메뉴 에러 : {}", commonException.getErrorCodes());
        return ErrorResponseDTO.toResponseEntity(commonException.getErrorCodes());
    }
}


