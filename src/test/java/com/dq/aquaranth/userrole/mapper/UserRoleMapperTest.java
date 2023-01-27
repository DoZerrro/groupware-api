package com.dq.aquaranth.userrole.mapper;

import com.dq.aquaranth.userrole.dto.paging.PageRequestDTO;
import com.dq.aquaranth.userrole.dto.response.*;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@Log4j2
class UserRoleMapperTest {

    @Autowired(required = false)
    UserRoleMapper userRoleMapper;

    @Test
    void findCompanyAll() {
        List<UserRoleCompanyDTO> list = userRoleMapper.findCompanyAll();
        list.forEach(log::info);

    }

    @Test
    void findCompany() {
        List<UserRoleCompanyDTO> list = userRoleMapper.findCompany("emp05");
        list.forEach(log::info);
        Assertions.assertNotNull(list);
    }

    @Test
    void findRoleGroupByOrgaNo() {
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                .page(1)
                .size(10)
                .orgaNo(1L)
//                .keyword1("관리")
                .build();
        List<UserRoleRoleGroupBasedListDTO> list = userRoleMapper.findRoleGroupByOrgaNo(pageRequestDTO);
        list.forEach(log::info);
        Assertions.assertNotNull(list);

        Integer total = userRoleMapper.findRoleGroupTotalByOrgaNo(pageRequestDTO);
        log.info("total : " + total);
        Assertions.assertNotNull(total);
        Assertions.assertNotEquals(total, 0);
    }

    @Test
    void findOrgaByRoleGroupNo() {
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                .page(1)
                .size(10)
                .orgaNo(1L)
                .roleGroupNo(1L)
                .build();
        List<UserRoleGroupBasedUserListDTO> list = userRoleMapper.findOrgaByRoleGroupNo(pageRequestDTO);
        list.forEach(log::info);
        Assertions.assertNotNull(list);

        Integer total = userRoleMapper.findOrgaTotalByRoleGroupNo(pageRequestDTO);
        log.info("total : " + total);
        Assertions.assertNotNull(total);
        Assertions.assertNotEquals(total, 0);
    }

    @Test
    void insertUserRole() {
    }

    @Test
    void removeOrgaRole() {
    }

    @Test
    void findUserListByOrgaNo() {
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                .page(1)
                .size(10)
                .orgaNo(1L)
                .keyword1("")
                .keyword2("")
                .build();
        List<UserRoleUserListBasedDTO> list = userRoleMapper.findUserListByOrgaNo(pageRequestDTO);
        list.forEach(log::info);
        Assertions.assertNotNull(list);

        Integer total = userRoleMapper.findUserListTotalByOrgaNo(pageRequestDTO);
        log.info("total : " + total);
        Assertions.assertNotNull(total);
        Assertions.assertNotEquals(total, 0);
    }

    @Test
    void findRoleGroupByUser() {
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                .page(1)
                .size(10)
                .orgaNo(21L)
                .build();
        List<UserRoleUserBasedRoleGroupDTO> list = userRoleMapper.findRoleGroupByUser(pageRequestDTO);
        list.forEach(log::info);
        Assertions.assertNotNull(list);

        Integer total = userRoleMapper.findRoleGroupTotalByUser(pageRequestDTO);
        log.info("total : " + total);
        Assertions.assertNotNull(total);
        Assertions.assertNotEquals(total, 0);
    }

    @Test
    void removeUserRole() {
    }
}
