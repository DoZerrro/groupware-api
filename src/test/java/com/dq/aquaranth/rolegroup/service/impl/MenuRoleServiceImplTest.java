package com.dq.aquaranth.rolegroup.service.impl;

import com.dq.aquaranth.rolegroup.domain.MenuRole;
import com.dq.aquaranth.rolegroup.dto.MenuRoleLnbDTO;
import com.dq.aquaranth.rolegroup.service.MenuRoleService;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Log4j2
class MenuRoleServiceImplTest {
    @Autowired
    private MenuRoleService menuRoleService;

    @Test
    @Rollback
    @DisplayName("메뉴권한 저장시 해당 권한그룹에 GNB 하위 메뉴권한을 전부 삭제합니다.")
    void addMenuDeleteMenuRole() {
        // given
        List<MenuRole> insertMenuRoles = new ArrayList<>();
        String moduleCode = "SYS";
        Long roleGroupNo = 12L;

        // when
        menuRoleService.save(insertMenuRoles, moduleCode, roleGroupNo);

        // then
        assertNull(menuRoleService.findByRoleGroupNoAndModuleCode(roleGroupNo, moduleCode));
    }

    @Test
    @Rollback
    @DisplayName("메뉴권한 저장시 메뉴권한을 초기화할때 메뉴코드가 존재하지 않으면 에러로그를 남깁니다.")
    void test_moduleCode_notExist() {
        // given
        List<MenuRole> insertMenuRoles = new ArrayList<>();
        Long roleGroupNo = 12L;
        insertMenuRoles.add(MenuRole.builder()
                .menuNo(1L)
                .roleGroupNo(roleGroupNo)
                .regUser("테스트유저")
                .regDate(LocalDateTime.now()).build());
        String moduleCode = "SDFASDC";

        // when
        menuRoleService.save(insertMenuRoles, moduleCode, roleGroupNo);
    }

    @Test
    @Rollback
    @DisplayName("권한그룹에 메뉴권한을 부여합니다.")
    void save() {
        // given
        String moduleCode = "SYS";
        Long roleGroupNo = 2L; // 더존 개발팀 권한
        Long[] menuNoList = {1L, 2L, 3L}; // 시스템설정, 조직관리, 메뉴관리

        List<MenuRole> insertMenuRoles = new ArrayList<>();
        for (Long menuNo : menuNoList) {
            insertMenuRoles.add(MenuRole.builder().menuNo(menuNo).regUser("종현테스트코드").roleGroupNo(roleGroupNo).build());
        }

        // when
        menuRoleService.save(insertMenuRoles, moduleCode, roleGroupNo);

        // then
        // 메뉴권한리스트를 다시 찾습니다.
        List<MenuRoleLnbDTO> result = menuRoleService.findByRoleGroupNoAndModuleCode(roleGroupNo, moduleCode);
        for (Long menuNo : menuNoList) {
            // 부여한 메뉴번호와 찾은메뉴번호가 같은 경우엔 checked 컬럼이 true 입니다.
            result.stream().filter(menuRoleLnbDTO -> Objects.equals(menuRoleLnbDTO.getMenuNo(), menuNo))
                    .forEach(menuRoleLnbDTO -> assertTrue(menuRoleLnbDTO.isChecked()));

            // 부여하지 않은 메뉴코드번호에 대해서는 checked 컬럼이 false 입니다.
            result.stream().filter(menuRoleLnbDTO -> !Objects.equals(menuRoleLnbDTO.getMenuNo(), menuNo))
                    .forEach(menuRoleLnbDTO -> assertFalse(menuRoleLnbDTO.isChecked()));
        }
    }
}
