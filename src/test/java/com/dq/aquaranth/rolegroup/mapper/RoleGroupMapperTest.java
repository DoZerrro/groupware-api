package com.dq.aquaranth.rolegroup.mapper;

import com.dq.aquaranth.login.domain.LoginUser;
import com.dq.aquaranth.rolegroup.domain.RoleGroup;
import com.dq.aquaranth.rolegroup.dto.PageRequestDTO;
import com.dq.aquaranth.rolegroup.dto.PageResponseDTO;
import com.dq.aquaranth.rolegroup.dto.RoleGroupResponseDTO;
import com.dq.aquaranth.rolegroup.dto.RoleGroupUpdateDTO;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Log4j2
class RoleGroupMapperTest {
    @Autowired(required = false)
    private RoleGroupMapper roleGroupMapper;

    @Test
    @DisplayName("권한그룹 목록을 가져옵니다.")
    void findAll() {
        for (RoleGroupResponseDTO param : roleGroupMapper.findAll()) {
            log.info(param);
        }
    }

    @Test
    @DisplayName("회사번호나 키워드를 입력받았다면 해당 조건에 맞는 권한그룹리스트를 조회합니다.")
    void findAll_exist_companyNo_and_roleGroupName() {
        // given
        Long searchCompanyNo = 2L;
//        String searchRoleGroupName = "mock";
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                .page(2)
//                .roleGroupName(searchRoleGroupName)
                .companyNo(searchCompanyNo)
                .build();

        // when
        List<RoleGroupResponseDTO> result = roleGroupMapper.getList(pageRequestDTO);

        // then
        for (RoleGroupResponseDTO dto : result) {
            assertEquals(dto.getCompanyNo(), searchCompanyNo);
//            assertTrue(dto.getRoleGroupName().contains(searchRoleGroupName));
            log.info(dto);
        }

    }

    @Test
    @DisplayName("권한그룹번호로 권한그룹을 조회합니다.")
    void findById() {
        Long roleGroupNo = 1L;
        log.info(roleGroupMapper.findById(roleGroupNo));
    }

    @Test
    @DisplayName("권한그룹을 추가합니다.")
    void insert() {
        // given
        String newRoleGroupName = "test 권한그룹2";
        boolean newRoleGroupUse = true;
        Long newCompanyNo = 2L; // MEGA ZONE

        RoleGroup insertRoleGroup = RoleGroup.builder()
                .roleGroupName(newRoleGroupName)
                .roleGroupUse(newRoleGroupUse)
                .companyNo(newCompanyNo)
                .regUser("종현")
                .build();

        // when
        roleGroupMapper.insert(insertRoleGroup); // 권한그룹 추가하기
        log.info(insertRoleGroup.getRoleGroupNo());

        // then
        assertNotNull(insertRoleGroup.getRoleGroupNo());
        log.info(insertRoleGroup);

        // 권한그룹 다중 모킹 데이터
//        for (int i = 0; i < 100; i++) {
//            roleGroupMapper.insert(RoleGroup.builder()
//                    .roleGroupName("권한그룹 mock 데이터 " + i)
//                    .roleGroupUse(true)
//                    .companyNo(2L)
//                    .regUser("test 종현")
//                    .build());
//        }
    }

    @Test
    @DisplayName("권한그룹번호로 권한그룹을 수정합니다.")
    void update() {
        // given
        Long updateRoleGroupNo = 13L;
        String updateRoleGroupName = "update222 권한그룹명66666"; // 바꿀 권한그룹명
        RoleGroup beforeDTO = roleGroupMapper.findById(updateRoleGroupNo); // 수정전  권한그룹
        boolean updateRoleGroupUse = !beforeDTO.isRoleGroupUse(); // 기존 사용여부의 반대로

        RoleGroupUpdateDTO updateDTO = RoleGroupUpdateDTO.builder()
                .roleGroupNo(updateRoleGroupNo)
                .roleGroupName(updateRoleGroupName)
                .roleGroupUse(updateRoleGroupUse)
                .companyNo(2L)
                .modUser("종현")
                .build();

        // when
        roleGroupMapper.update(updateDTO);

        // then
        RoleGroup afterDTO = roleGroupMapper.findById(updateRoleGroupNo);
        // 권한 그룹 수정이 일어나지 않았다면, updateRoleGroupNo에 대한 권한그룹명은 그대로일 것이다.
        assertEquals(afterDTO.getRoleGroupName(), updateRoleGroupName);
        // 사용여부를 기존 권한그룹 사용여부의 반대값으로 수정하였기 때문에 사용여부는 서로 달라야 할 것이다.
        assertNotEquals(beforeDTO.isRoleGroupUse(), afterDTO.isRoleGroupUse());

        log.info("수정 후 권한그룹 => {}", afterDTO);
    }

    @Test
    @DisplayName("권한그룹번호로 권한그룹을 삭제합니다.")
    void deleteById() {
        // given
        Long deleteRoleGroupNo = 9L;

        // when
        roleGroupMapper.deleteById(deleteRoleGroupNo);

        // then
        assertNull(roleGroupMapper.findById(deleteRoleGroupNo));
    }

    @Test
    @DisplayName("로그인한 사원의 권한그룹들을 가져옵니다.")
    void findRoleGroupsByLoginUser() {
        // given
        String username = "tndus";
        Long companyNo = 7L; // MEGAZONE(orgaNo=2)
        Long deptNo = 7L; // 개발팀(orgaNo=7)

        LoginUser loginUser = LoginUser.builder()
                .loginCompanyNo(companyNo)
                .loginDeptNo(deptNo)
                .username(username).build();

        // when
        List<RoleGroup> roleGroups = roleGroupMapper.findRoleGroupsByLoginUser(loginUser);

        // then
        for (RoleGroup roleGroup : roleGroups) {
            log.info(roleGroup);
        }
    }

    @Test
    @DisplayName("마지막으로 조회한 권한그룹리스트의 갯수를 요청합니다.")
    public void getTotal() {
        roleGroupMapper.getList(PageRequestDTO.builder()
                .page(2)
                .roleGroupName("asd")
//                .companyNo(2L)
                .build());

        int total = roleGroupMapper.getTotal();

        log.info(total);
    }

    @Test
    @DisplayName("페이지 번호에 맞는 권한그룹리스트를 조회합니다.")
    void getRoleGroupPageNo() {
        // given
        int pageNo = 1;
        PageRequestDTO pageReq = PageRequestDTO.builder()
                .page(pageNo)
                .build();

        // then
        List<RoleGroupResponseDTO> list = roleGroupMapper.getList(pageReq);

        // when
        for (RoleGroupResponseDTO param : list) {
            log.info(param);
        }
    }
}
