package com.dq.aquaranth.menu.service;

import com.dq.aquaranth.menu.dto.request.MenuQueryDTO;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Log4j2
class DefaultMenuServiceTest {
    @Autowired
    DefaultMenuService defaultMenuService;

    @Test
    @DisplayName("메뉴를 복수건 조회합니다.")
    void findAllBy() {
        MenuQueryDTO menuQueryDTO = MenuQueryDTO.builder()
                .menuNo(8L)
                .upperMenuNo(1L)
                .menuCode("SYS")
                .keyword("asdf")
                .build();
        String username = "master";

        defaultMenuService.findAllBy(menuQueryDTO, username).forEach(log::info);
    }

    @Test
    @DisplayName("메뉴에 맞는 모듈 경로와 메뉴번호를 반환합니다.")
    void initRoutes() {
        defaultMenuService.initRoutes().forEach(log::info);
    }
}
