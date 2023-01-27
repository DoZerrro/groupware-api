package com.dq.aquaranth.admin.service;

import com.dq.aquaranth.company.dto.CompanyInformationDTO;
import com.dq.aquaranth.company.service.CompanyService;
import com.dq.aquaranth.dept.dto.DeptRegisterDTO;
import com.dq.aquaranth.dept.service.DeptService;
import com.dq.aquaranth.emp.dto.emp.EmpDTO;
import com.dq.aquaranth.emp.dto.emp.EmpMappingDTO;
import com.dq.aquaranth.emp.dto.orga.EmpOrgaDTO;
import com.dq.aquaranth.emp.mapper.EmpMapper;
import com.dq.aquaranth.emp.mapper.EmpMappingMapper;
import com.dq.aquaranth.rolegroup.domain.MenuRole;
import com.dq.aquaranth.rolegroup.domain.RoleGroup;
import com.dq.aquaranth.rolegroup.mapper.MenuRoleMapper;
import com.dq.aquaranth.rolegroup.mapper.RoleGroupMapper;
import com.dq.aquaranth.userrole.dto.request.UserRoleReqInsertOrgaRoleDTO;
import com.dq.aquaranth.userrole.service.UserRoleService;
import lombok.extern.log4j.Log4j2;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Rollback;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Log4j2
public class AdminServiceTest {
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    CompanyService companyService;

    @Autowired
    DeptService deptService;

    @Autowired(required = false)
    EmpMapper empMapper;

    @Autowired(required = false)
    RoleGroupMapper roleGroupMapper;

    @Autowired(required = false)
    MenuRoleMapper menuRoleMapper;

    @Autowired(required = false)
    EmpMappingMapper empMappingMapper;

    @Autowired
    UserRoleService userRoleService;

    @Test
    @DisplayName("사원테이블에 master 사용자 생성")
    void createMasterEmp() {
        EmpDTO masterUser = EmpDTO.builder()
                .empName("마스터사용자")
                .username("master")
                .password(passwordEncoder.encode("master"))
                .regUser("master")
                .gender("남성")
                .firstHiredDate(LocalDate.now())
                .build();

        empMapper.insert(masterUser);

        assertNotNull(masterUser.getEmpNo());
    }

    @Test
    @DisplayName("마스터 회사를 생성합니다.")
    void createMasterCompany() {
        String username = "master";

        // 1. 회사 추가
        CompanyInformationDTO companyInformationDTO = CompanyInformationDTO
                .builder()
                .companyName("마스터회사")
                .companyAddress(".")
                .companyTel(".")
                .ownerName("강도영")
                .businessNumber(".")
                .foundingDate(LocalDate.now())
                .companyUse(true)
                .build();

        Long resultCompanyCreate
                = companyService.insert(companyInformationDTO, username);

        log.info("회사 추가 결과 : " + resultCompanyCreate);
    }


    @Test
    @DisplayName("마스터 부서를 생성합니다.")
    void createMasterDept(){
        int resultDeptCreate = 0;

        // 부서 등록 할 때 필요한 정보를 담을 DTO 생성
        DeptRegisterDTO deptRegisterDTO = DeptRegisterDTO
                .builder()
                .deptName("마스터부서")
                .deptDesc("마스터부서")
                .username("master")
                .upperDeptNo(0L)
                .companyNo(1L)
                .build();

        // 만든 DTO 를 서비스에 넣기
        resultDeptCreate = deptService.insert(deptRegisterDTO);

        // 부서 등록 결과 반환
        log.info(resultDeptCreate);
    }

    @Test
    @DisplayName("마스터 조직(사원 추가)과 사원 매핑테이블을 생성합니다.")
    void createMasterEmpMappingAndOrga(){
        Long masterDeptNo = 1L; // 마스터부서번호
        Long masterEmpNo = 1L;  // 마스터사원번호

        EmpOrgaDTO empOrgaDTO = EmpOrgaDTO
                .builder()
                .orgaType("emp")
                .deptNo(masterDeptNo)
                .regUser("master")
                .build();

        empMapper.insertOrga(empOrgaDTO);

        Long orgaLastId = empOrgaDTO.getOrgaNo();

        EmpMappingDTO empMappingDTO = EmpMappingDTO
                .builder()
                .orgaNo(orgaLastId)
                .empNo(masterEmpNo)
                .empRank("마스터직급")
                .empRole("ROLE_ADMIN")
                .regUser("master")
                .hiredDate(LocalDate.now())
                .build();

        empMappingMapper.empMappingInsert(empMappingDTO);
    }

    @Test
    @DisplayName("마스터 권한그룹을 생성합니다.")
    void insertMasterRoleGroup() {
        // given
        String newRoleGroupName = "마스터 권한그룹";
        boolean newRoleGroupUse = true;
        Long newCompanyNo = 1L; // 마스터 회사

        RoleGroup insertRoleGroup = RoleGroup.builder()
                .roleGroupName(newRoleGroupName)
                .roleGroupUse(newRoleGroupUse)
                .companyNo(newCompanyNo)
                .regUser("master")
                .build();

        // when
        roleGroupMapper.insert(insertRoleGroup); // 권한그룹 추가하기
        log.info(insertRoleGroup.getRoleGroupNo());

        // then
        assertNotNull(insertRoleGroup.getRoleGroupNo());
        log.info(insertRoleGroup);
    }

    @Test
    @DisplayName("마스터 권한그룹에 모든 메뉴권한들을 추가합니다.")
    @Rollback
    void insertMasterMenuRole() {
        // given
        List<Long> menuNoList = List.of(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L, 9L, 10L, 11L);
        Long roleGroupNo = 1L; // 마스터 권한그룹
        String regUser = "master";

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
        }
    }

    @Test
    @DisplayName("마스터 권한그룹을 마스터 조직정보에 매핑합니다.")
    void createMasterOrgaRoleMapping() {
        // 권한 그룹을 부여할 조직의 orgaNo 리스트
        List<Long> orgaNoList = new ArrayList<>();
        // master 의 orgaNo 담기
        orgaNoList.add(1L);
        orgaNoList.add(2L);
        // tbl_orga_role에 넣을 데이터를 담은 DTO 생성
        UserRoleReqInsertOrgaRoleDTO dto = UserRoleReqInsertOrgaRoleDTO
                .builder()
                .roleGroupNo(1L)
                .username("master")
                .orgaNoList(orgaNoList)
                .build();
        // 실행
        Assertions.assertThat(userRoleService.insertUserRole(dto)).isNotNull();
    }
}
