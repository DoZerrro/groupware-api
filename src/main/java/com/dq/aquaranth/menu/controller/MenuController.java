package com.dq.aquaranth.menu.controller;


import com.dq.aquaranth.menu.annotation.MenuCode;
import com.dq.aquaranth.menu.dto.request.MenuQueryDTO;
import com.dq.aquaranth.menu.dto.response.ErrorResponseDTO;
import com.dq.aquaranth.menu.dto.response.MenuImportResponseDTO;
import com.dq.aquaranth.menu.dto.response.MenuResponseDTO;
import com.dq.aquaranth.menu.dto.response.MenuTreeResponseDTO;
import com.dq.aquaranth.menu.exception.ErrorCodes;
import com.dq.aquaranth.menu.service.MenuService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 일반 메뉴 컨트롤러 입니다.
 * 권한그룹에 속한 메뉴만 조회가 가능합니다.
 *
 * @author 김민준
 */
@Log4j2
@RestController
@RequestMapping("/api/menu")
@RequiredArgsConstructor
@MenuCode
public class MenuController {

    private final MenuService menuService;


    /**
     * 메뉴를 복수건 조회합니다.
     *
     * @param menuQueryDTO
     * @param httpServletRequest
     * @return
     */
    @GetMapping
    public List<MenuTreeResponseDTO> findAllBy(MenuQueryDTO menuQueryDTO, HttpServletRequest httpServletRequest) {
        return menuService.findAllBy(menuQueryDTO, httpServletRequest.getUserPrincipal().getName());
    }

    /**
     * 클라이언트 앱 초기화용 메소드입니다.
     * 메뉴에 맞는 모듈 경로와 메뉴번호를 반환합니다.
     *
     * @return
     */
    @GetMapping("/init")
    public List<MenuImportResponseDTO> initRoutes() {
        return menuService.initRoutes();
    }

    /**
     * 인터셉터에서 발생한
     * 권한 에러를 처리합니다.
     * @return
     */
    @GetMapping("/error")
    public ResponseEntity<ErrorResponseDTO> response(){
        return ErrorResponseDTO.toResponseEntity(ErrorCodes.UNAUTHORIZED_MEMBER);
    }

}
