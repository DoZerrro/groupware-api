package com.dq.aquaranth.menu.mapper;

import com.dq.aquaranth.menu.dto.request.MenuInsertDTO;
import com.dq.aquaranth.menu.dto.request.MenuQueryDTO;
import com.dq.aquaranth.menu.dto.request.MenuUpdateDTO;
import com.dq.aquaranth.menu.dto.response.MenuResponseDTO;
import com.dq.aquaranth.menu.dto.response.MenuTreeResponseDTO;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Log4j2
@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class MenuConfigurationMapperTest {

    @Autowired
    MenuConfigurationMapper menuConfigurationMapper;

    @Test
    @DisplayName(value = "시스템 메뉴의 하위 메뉴들 조회")
    void treeOnSystem() {
        List<MenuTreeResponseDTO> menuTreeResponseDTOS = menuConfigurationMapper.findUnderMenusBy(MenuQueryDTO.builder().menuNo(1L).build());
        menuTreeResponseDTOS.forEach(log::info);
    }

    @Test
    @DisplayName(value = "GNB 메뉴 조회")
    void treeGnb() {
        List<MenuTreeResponseDTO> menuTreeResponseDTOS = menuConfigurationMapper.findUnderMenusBy(MenuQueryDTO.builder().keyword("gnb").build());
        menuTreeResponseDTOS.forEach(log::info);
    }

    @Test
    @DisplayName(value = "전체 메뉴를 조회")
    void findAll() {
        MenuQueryDTO menuQueryDTO = MenuQueryDTO.builder().build();
        List<MenuResponseDTO> menuResponseDTOList = menuConfigurationMapper.findAllBy(menuQueryDTO);
        menuResponseDTOList.forEach(log::info);
        assertThat(menuResponseDTOList).isNotNull();
    }

    @Test
    @DisplayName(value = "메뉴 상태 업데이트")
    void update() {
        Long menuNo = 12L;
        String menuName = "구글 드라이브";
        MenuUpdateDTO menuUpdateDTO = MenuUpdateDTO.builder().menuNo(menuNo).menuName(menuName).mainFlag(false).menuOrder(99L).build();
        menuConfigurationMapper.update(menuUpdateDTO);
    }

    @Test
    @DisplayName(value = "메뉴 추가")
    void insert() {
        MenuInsertDTO menuInsertDTO = MenuInsertDTO.builder().upperMenuNo(1L).menuName("급여 관리").mainFlag(true).menuCode("ORGA0040").menuPath("/SYS/ORGA/ORGA0040").menuOrder(99L).uuid("").filename("").regUser("admin").build();
        menuConfigurationMapper.insert(menuInsertDTO);
        log.info(menuInsertDTO);
    }


    @Test
    void findBy() {
        MenuQueryDTO menuQueryDTO = MenuQueryDTO.builder()
                .menuNo(1L)
                .build();
        log.info(menuConfigurationMapper.findBy(menuQueryDTO));
    }

    @Test
    void findMenuDetailsBy() {
        MenuQueryDTO menuQueryDTO = MenuQueryDTO.builder()
                .menuNo(2L)
                .build();
        log.info(menuConfigurationMapper.findMenuDetailsBy(menuQueryDTO));
    }
}
