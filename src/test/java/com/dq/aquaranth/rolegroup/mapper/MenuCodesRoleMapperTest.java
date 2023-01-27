package com.dq.aquaranth.rolegroup.mapper;

import com.dq.aquaranth.rolegroup.domain.MenuRole;
import com.dq.aquaranth.rolegroup.dto.MenuRoleLnbDTO;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Log4j2
class MenuCodesRoleMapperTest {
    @Autowired(required = false)
    private MenuRoleMapper menuRoleMapper;

    @Test
    @DisplayName("권한그룹이 가지고있는 메뉴권한 목록을 가지고 옵니다.")
    void findAll() {
        Long roleGroupNo = 12L;
        log.info(menuRoleMapper.findAllByRoleGroupNo(roleGroupNo));
    }

    @Test
    @DisplayName("권한그룹에 메뉴권한들을 추가합니다.")
    @Rollback
    void insert() {
        // given
        List<Long> menuNoList = List.of(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L, 9L, 10L, 11L, 12L, 13L);
        Long roleGroupNo = 12L;
        String regUser = "테스트종현";

        for (long menuNo : menuNoList) {
            MenuRole insertMenuRole = MenuRole.builder()
                    .menuNo(menuNo)
                    .roleGroupNo(roleGroupNo)
                    .regUser(regUser)
                    .regDate(LocalDateTime.now())
                    .build();

//            when
            menuRoleMapper.insert(insertMenuRole);
            log.info("추가된 메뉴권한 번호" + insertMenuRole.getMenuNo());

//            then
        }
    }

    @Test
    @DisplayName("권한그룹번호로 모든 메뉴권한을 삭제합니다.")
    void deleteById() {
        // given
        Long deleteRoleGroupNo = 19L;

        // when
        menuRoleMapper.deleteAllByRoleGroupNo(deleteRoleGroupNo);

        // then
        assertEquals(0, menuRoleMapper.findAllByRoleGroupNo(deleteRoleGroupNo).size());
    }

    @Test
    @DisplayName("gnb메뉴를 포함한 하위메뉴들의 메뉴권한을 삭제합니다.")
    void deleteByRoleGroupNoAndModuleCode() {
        // given
        Long roleGroupNo = 12L;
        String moduleCode = "SYS";

        // when
        menuRoleMapper.deleteByRoleGroupNoAndModuleCode(roleGroupNo, moduleCode);

        // then
        assertNotNull(menuRoleMapper.findAllByRoleGroupNo(roleGroupNo));
    }

    @Test
    @DisplayName("해당 권한그룹이 GNB를 포함한 하위메뉴에 대한 메뉴권한을 조회합니다.")
    void findByRoleGroupNoAndModuleCode() {
        // given
        Long roleGroupNo = 2L;
        String moduleCode = "SYS";

        // when
        List<MenuRoleLnbDTO> list = menuRoleMapper.findByRoleGroupNoAndModuleCode(roleGroupNo, moduleCode);

        // then
        for (MenuRoleLnbDTO param : list) {
            log.info(param);
        }
    }
}
