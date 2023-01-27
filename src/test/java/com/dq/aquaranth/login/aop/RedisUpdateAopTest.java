package com.dq.aquaranth.login.aop;

import com.dq.aquaranth.login.service.RedisService;
import com.dq.aquaranth.rolegroup.domain.MenuRole;
import com.dq.aquaranth.rolegroup.service.MenuRoleService;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Log4j2
class RedisUpdateAopTest {
    @Autowired
    MenuRoleService menuRoleService;

    @Autowired
    RedisService redisService;

    @Test
    @DisplayName("메뉴권한 수정시 Redis 에 정보가 업데이트 됩니다.")
    void menuRoleUpdateRedisUpdate() {
        // given
        String username = "ehek01";
        Long roleGroupNo = 1L;
        Long[] menuNoList = {1L, 2L, 3L, 4L, 5L, 6L, 7L};
        String moduleCode = "SYS";
        List<MenuRole> insertMenuRoles = new ArrayList<>();
        for (Long menuNo : menuNoList) {
            insertMenuRoles.add(MenuRole.builder()
                    .menuNo(menuNo)
                    .roleGroupNo(roleGroupNo)
                    .regDate(LocalDateTime.now())
                    .regUser(username).build());
        }

        // when
        menuRoleService.save(insertMenuRoles, moduleCode, roleGroupNo);

        // then
        Object result = redisService.getCacheObject(username);
        log.info(result);
    }

    @Test
    void test() {
        Object result = redisService.getCacheObject("ehek01");
        log.info(result);
    }
}
