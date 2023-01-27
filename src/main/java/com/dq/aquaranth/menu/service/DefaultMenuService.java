package com.dq.aquaranth.menu.service;

import com.dq.aquaranth.login.dto.LoginUserInfo;
import com.dq.aquaranth.login.service.RedisService;
import com.dq.aquaranth.login.service.UserSessionService;
import com.dq.aquaranth.menu.dto.request.MenuQueryDTO;
import com.dq.aquaranth.menu.dto.response.MenuImportResponseDTO;
import com.dq.aquaranth.menu.dto.response.MenuTreeResponseDTO;
import com.dq.aquaranth.menu.mapper.MenuMapper;
import com.dq.aquaranth.menu.objectstorage.dto.request.ObjectGetRequestDTO;
import com.dq.aquaranth.menu.objectstorage.service.ObjectStorageService;
import com.dq.aquaranth.rolegroup.domain.RoleGroup;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 일반 메뉴 서비스입니다.
 *
 * @author 김민준
 */
@Log4j2
@Service
@RequiredArgsConstructor
public class DefaultMenuService implements MenuService {


    private final MenuMapper menuMapper;
    private final ObjectStorageService objectStorageService;
    private final UserSessionService userSessionService;


    /**
     * 메뉴를 복수건 조회합니다.
     *
     * @param menuQueryDTO
     * @param username
     * @return
     */
    @Override
    public List<MenuTreeResponseDTO> findAllBy(MenuQueryDTO menuQueryDTO, String username) {
        LoginUserInfo loginUserInfo = userSessionService.findUserInfoInRedis(username);
        List<Long> roleGroupNo = loginUserInfo.getRoleGroups()
                .stream().map(RoleGroup::getRoleGroupNo)
                .collect(Collectors.toList());
        List<MenuTreeResponseDTO> menuTreeResponseDTOS = menuMapper.findAllBy(menuQueryDTO, roleGroupNo);

        menuTreeResponseDTOS.forEach(menuTreeResponseDTO -> {
            try {
                menuTreeResponseDTO.setIconUrl(objectStorageService.getObject(ObjectGetRequestDTO.builder()
                                .filename(menuTreeResponseDTO.getUuid() + menuTreeResponseDTO.getFilename())
                                .time((int) TimeUnit.DAYS.toHours(7))
                                .build())
                        .getUrl());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        return menuTreeResponseDTOS;
    }

    /**
     * 클라이언트 앱 초기화용 메소드입니다.
     * 메뉴에 맞는 모듈 경로와 메뉴번호를 반환합니다.
     *
     * @return
     */
    @Override
    public List<MenuImportResponseDTO> initRoutes() {
        return menuMapper.initRoutes();
    }
}
