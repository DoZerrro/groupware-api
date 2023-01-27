package com.dq.aquaranth.menu.mapper;

import com.dq.aquaranth.menu.dto.request.MenuQueryDTO;
import com.dq.aquaranth.menu.dto.request.MenuQueryUserDTO;
import com.dq.aquaranth.menu.dto.response.MenuImportResponseDTO;
import com.dq.aquaranth.menu.dto.response.MenuResponseDTO;
import com.dq.aquaranth.menu.dto.response.MenuTreeResponseDTO;
import com.dq.aquaranth.menu.exception.CommonException;
import com.dq.aquaranth.menu.exception.ErrorCodes;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Log4j2
@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class MenuMapperTest {
    @Autowired
    MenuMapper menuMapper;

    @Test
    @DisplayName("모든메뉴를 조회합니다.")
    void initRoutes() {
        menuMapper.initRoutes().forEach(log::info);
    }

    @Test
    @DisplayName("권한그룹, 메뉴정보별 메뉴조회")
    void findAllBy() {
        // given
        MenuQueryDTO findDTO = MenuQueryDTO.builder()
//                .menuNo()
//                .menuCode()
//                .upperMenuNo()
//                .keyword()
                .build();

        List<Long> roleGroupNoList = List.of(1L, 2L);

        // when
        menuMapper.findAllBy(findDTO, roleGroupNoList).forEach(log::info);
    }
}
