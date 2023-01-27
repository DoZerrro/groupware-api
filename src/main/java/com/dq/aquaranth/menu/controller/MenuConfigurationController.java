package com.dq.aquaranth.menu.controller;

import com.dq.aquaranth.menu.annotation.MenuCode;
import com.dq.aquaranth.menu.constant.MenuCodes;
import com.dq.aquaranth.menu.dto.request.MenuInsertDTO;
import com.dq.aquaranth.menu.dto.request.MenuQueryDTO;
import com.dq.aquaranth.menu.dto.request.MenuUpdateDTO;
import com.dq.aquaranth.menu.dto.response.MenuDetailResponseDTO;
import com.dq.aquaranth.menu.dto.response.MenuResponseDTO;
import com.dq.aquaranth.menu.dto.response.MenuTreeResponseDTO;
import com.dq.aquaranth.menu.objectstorage.dto.request.MultipartFileDTO;
import com.dq.aquaranth.menu.service.MenuConfigurationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 메뉴 설정 컨트롤러 입니다.
 *
 * @author 김민준
 */
@Log4j2
@RestController
@RequestMapping("/api/menu/config")
@RequiredArgsConstructor
@MenuCode(MenuCodes.ROLE0030)
public class MenuConfigurationController {

    private final MenuConfigurationService menuConfigurationService;

    /**
     * 메뉴를 단건 조회합니다.
     *
     * @param menuQueryDTO
     * @return
     */
    @GetMapping
    public MenuResponseDTO findBy(final MenuQueryDTO menuQueryDTO) {
        return menuConfigurationService.findBy(menuQueryDTO);
    }

    /**
     * 메뉴를 복수건 조회합니다.
     *
     * @param menuQueryDTO
     * @return
     */
    @GetMapping("/list")
    public List<MenuResponseDTO> findAllBy(final MenuQueryDTO menuQueryDTO) {
        return menuConfigurationService.findAllBy(menuQueryDTO);
    }

    /**
     * DTO로 전달받은 메뉴의 하위 메뉴들만 조회합니다.
     *
     * @param menuTreeQueryDTO
     * @return
     */
    @GetMapping("/list/under")
    public List<MenuTreeResponseDTO> findUnderMenusBy(final MenuQueryDTO menuQueryDTO) {
        return menuConfigurationService.findUnderMenusBy(menuQueryDTO);
    }

    /**
     * 메뉴를 상세조회 합니다.
     *
     * @param menuQueryDTO
     * @return
     */
    @GetMapping("/detail")
    public MenuDetailResponseDTO findMenuDetailsBy(final MenuQueryDTO menuQueryDTO) {
        return menuConfigurationService.findMenuDetailsBy(menuQueryDTO);
    }

    /**
     * 메뉴를 추가합니다.
     *
     * @param menuInsertDTO
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/insert", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Void> insert(final MenuInsertDTO menuInsertDTO, HttpServletRequest httpServletRequest) throws Exception {
        menuConfigurationService.insert(menuInsertDTO, httpServletRequest.getUserPrincipal().getName());
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * 메뉴를 삭제합니다.
     *
     * @param menuQueryDTO
     * @return
     */
    @DeleteMapping("/delete")
    public ResponseEntity<Void> delete(final MenuQueryDTO menuQueryDTO) {
        menuConfigurationService.delete(menuQueryDTO);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * 메뉴 정보를 업데이트 합니다.
     */
    @PutMapping("/update")
    public ResponseEntity<Void> update(@RequestBody final MenuUpdateDTO menuUpdateDTO, HttpServletRequest httpServletRequest) {
        menuConfigurationService.update(menuUpdateDTO, httpServletRequest.getUserPrincipal().getName());
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * 메뉴 아이콘을 업데이트 합니다.
     */
    @PutMapping(value = "/update/icon", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Void> updateByMenuIcon(final MultipartFileDTO multipartFileDTO, HttpServletRequest httpServletRequest) throws Exception {
        menuConfigurationService.updateIcon(multipartFileDTO, httpServletRequest.getUserPrincipal().getName());
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
