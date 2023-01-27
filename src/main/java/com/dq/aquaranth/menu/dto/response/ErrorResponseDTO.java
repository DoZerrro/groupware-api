package com.dq.aquaranth.menu.dto.response;

import com.dq.aquaranth.menu.exception.ErrorCodes;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

/**
 * 예외용 DTO입니다.
 * 클라이언트로 공통된 예외 메시지를 전송합니다.
 *
 * @author 김민준
 */
@Getter
@Builder
public class ErrorResponseDTO {
    private final LocalDateTime timestamp = LocalDateTime.now();
    private final int status;
    private final String error;
    private final String code;
    private final String message;
    private final Long detailErrorCode;

    public static ResponseEntity<ErrorResponseDTO> toResponseEntity(ErrorCodes errorCodes) {
        return ResponseEntity.status(errorCodes.getHttpStatus()).body(ErrorResponseDTO.builder().status(errorCodes.getHttpStatus().value()).error(errorCodes.getHttpStatus().name()).code(errorCodes.name()).message(errorCodes.getDetail()).detailErrorCode(errorCodes.getDetailErrorCode()).build());

    }
}
