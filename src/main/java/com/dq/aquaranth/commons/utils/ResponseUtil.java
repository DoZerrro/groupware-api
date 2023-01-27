package com.dq.aquaranth.commons.utils;

import com.dq.aquaranth.menu.exception.ErrorCodes;
import com.dq.aquaranth.menu.dto.response.ErrorResponseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

public class ResponseUtil {
    public ResponseUtil() {
        throw new IllegalArgumentException("util class 는 생성할 수 없습니다.");
    }

    public static void sendError(HttpServletResponse response, ErrorCodes errorCodes) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setStatus(errorCodes.getHttpStatus().value());
        response.setContentType(APPLICATION_JSON_VALUE);
        new ObjectMapper().registerModule(new JavaTimeModule()).writeValue(response.getWriter(), ErrorResponseDTO.toResponseEntity(errorCodes));
    }
}

