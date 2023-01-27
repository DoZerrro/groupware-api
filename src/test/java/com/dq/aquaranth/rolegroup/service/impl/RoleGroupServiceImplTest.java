package com.dq.aquaranth.rolegroup.service.impl;

import com.dq.aquaranth.rolegroup.domain.RoleGroup;
import com.dq.aquaranth.rolegroup.mapper.MenuRoleMapper;
import com.dq.aquaranth.rolegroup.service.MenuRoleService;
import com.dq.aquaranth.rolegroup.service.RoleGroupService;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Log4j2
class RoleGroupServiceImplTest {
    @Autowired
    RoleGroupService roleGroupService;
    @Autowired(required = false)
    MenuRoleMapper menuRoleMapper;

    @Test
    @DisplayName("권한그룹을 삭제할때, 없는번호라면 예외를 반환하나요?")
    void delete_fail_throwException() {
        // given
        Long deleteNo = 6L; // 존재하지 않는 권한그룹번호
        RoleGroup findDTO = roleGroupService.findById(deleteNo);

        // when & then
        assertThrows(RuntimeException.class, () -> roleGroupService.deleteById(deleteNo));
    }

    @Test
    @DisplayName("권한그룹을 삭제시 조직권한, 메뉴권한테이블에 연관된 데이터들도 전부 삭제합니다.")
    void delete_success() {
        // given
        Long deleteRoleGroupNo = 5L; // 존재하는 권한그룹번호
        RoleGroup findDTO = roleGroupService.findById(deleteRoleGroupNo);
        log.info("삭제할 권한그룹 {}", findDTO);

        // when
        roleGroupService.deleteById(deleteRoleGroupNo);

        // then
        assertNull(roleGroupService.findById(deleteRoleGroupNo));
        assertNull(menuRoleMapper.findAllByRoleGroupNo(deleteRoleGroupNo));
    }
}
