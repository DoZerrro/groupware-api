package com.dq.aquaranth.menu.service;

import com.dq.aquaranth.menu.dto.request.MenuQueryDTO;
import com.dq.aquaranth.menu.dto.response.MenuResponseDTO;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Log4j2
class DefaultMenuConfigurationServiceTest {
    @Autowired
    DefaultMenuConfigurationService defaultMenuConfigurationService;

    @Test
    @DisplayName("메뉴를 단건 조회합니다.(아이콘 URL과 함께 반환합니다.)")
    void findBy() {
        MenuResponseDTO result = defaultMenuConfigurationService.findBy(MenuQueryDTO.builder()
                .menuNo(1L)
                .menuCode("SYS")
                .build());

        log.info(result);
    }

    @Test
    @DisplayName("메뉴를 복수건 조회합니다.(아이콘 URL과 함께 반환합니다.)")
    void findAllBy() {
        defaultMenuConfigurationService.findAllBy(MenuQueryDTO.builder()
                .upperMenuNo(1L)
                .build()).forEach(log::info);
    }

    @Test
    void findUnderMenusBy() {
    }
}
