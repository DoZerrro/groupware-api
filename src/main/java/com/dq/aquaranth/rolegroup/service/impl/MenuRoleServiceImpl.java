package com.dq.aquaranth.rolegroup.service.impl;

import com.dq.aquaranth.login.constant.RedisKeys;
import com.dq.aquaranth.login.service.RedisService;
import com.dq.aquaranth.menu.dto.request.MenuQueryDTO;
import com.dq.aquaranth.menu.mapper.MenuConfigurationMapper;
import com.dq.aquaranth.rolegroup.domain.MenuRole;
import com.dq.aquaranth.rolegroup.domain.RoleGroup;
import com.dq.aquaranth.rolegroup.dto.MenuRoleLnbDTO;
import com.dq.aquaranth.rolegroup.mapper.MenuRoleMapper;
import com.dq.aquaranth.rolegroup.mapper.RoleGroupMapper;
import com.dq.aquaranth.rolegroup.service.MenuRoleService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Log4j2
public class MenuRoleServiceImpl implements MenuRoleService {
    private final MenuRoleMapper menuRoleMapper;
    private final MenuConfigurationMapper menuMapper;
    private final RedisService redisService;
    private final RoleGroupMapper roleGroupMapper;


    @Override
    public List<MenuRoleLnbDTO> findByRoleGroupNoAndModuleCode(Long roleGroupNo, String moduleCode) {
        return menuRoleMapper.findByRoleGroupNoAndModuleCode(roleGroupNo, moduleCode);
    }

    @Override
    public List<MenuRole> insert(List<MenuRole> insetMenuRoles) {
        List<MenuRole> result = new ArrayList<>();

        for (MenuRole menuRole : insetMenuRoles) {
            log.info(menuRole.getRoleGroupNo() + "번 권한그룹에 {}번 메뉴권한을 추가합니다.", menuRole.getMenuNo());
            menuRoleMapper.insert(menuRole);
        }

        return result;
    }

    @Override
    public void deleteByRoleGroupNo(Long roleGroupNo) {
        menuRoleMapper.deleteAllByRoleGroupNo(roleGroupNo);
    }

    @Override
    @Transactional
    public void save(List<MenuRole> insertMenuRoles, String moduleCode, Long roleGroupNo) {
        if (menuMapper.findAllBy(MenuQueryDTO.builder().menuCode(moduleCode).build()).isEmpty()) {
            log.error("GNB MenuCode {}는 존재하지 않습니다.", moduleCode);
            return;
        }

        menuRoleMapper.deleteByRoleGroupNoAndModuleCode(roleGroupNo, moduleCode);
        log.info("{}번 권한그룹에 {}모듈을 포함한 하위메뉴들의 권한이 모두 삭제되었습니다.", roleGroupNo, moduleCode);

        for (MenuRole menuRole : insertMenuRoles) {
            menuRoleMapper.insert(menuRole);
            log.info("{}번 권한그룹에 {}번 메뉴가 정상적으로 저장되었습니다.", menuRole.getRoleGroupNo(), menuRole.getMenuNo());
        }

        redisService.updateMenuRoles();
    }
}
