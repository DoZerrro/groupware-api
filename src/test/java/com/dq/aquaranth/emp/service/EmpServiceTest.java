package com.dq.aquaranth.emp.service;

import com.dq.aquaranth.emp.dto.emp.EmpDTO;
import com.dq.aquaranth.emp.dto.emp.EmpMappingDTO;
import com.dq.aquaranth.emp.dto.emp.EmpUpdateDTO;
import com.dq.aquaranth.emp.dto.orga.EmpOrgaDTO;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;

@SpringBootTest
@Log4j2
class EmpServiceTest {

    @Autowired(required = false)
    private EmpService empService;
    @Autowired(required = false)
    PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("사원 정보를 추가합니다.")
    void insert() {
        EmpOrgaDTO empOrgaDTO = EmpOrgaDTO.builder()
                .deptNo(7L)
                .regUser("tndus")
                .build();

        Long orgaId = empOrgaDTO.getOrgaNo();

        EmpDTO empDTO = EmpDTO.builder()
                .empName("박다린")
                .username("darin111")
                .password(passwordEncoder.encode("emp"))
                .regUser("tndus")
                .gender("여성")
                .firstHiredDate(LocalDate.now())
                .build();

        Long empNo = empDTO.getEmpNo();

        EmpMappingDTO empMappingDTO = EmpMappingDTO
                .builder()
                .orgaNo(orgaId)
                .empNo(empNo)
                .empRank("사원")
                .regUser("tndus")
                .build();

        try {
            log.info(empService.insert(empOrgaDTO, empDTO, empMappingDTO));
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

    }

    @Test
    @DisplayName("모든 사원 정보를 조회합니다.")
    void findAll() {
        log.info(empService.findAll());
    }

    @Test
    @DisplayName("사원 번호로 사원을 조회합니다.")
    void findById() {
        log.info(empService.findById(2L));
    }

    @Test
    @DisplayName("사원 정보를 수정합니다.")
    void update() {
        EmpUpdateDTO empUpdateDTO = EmpUpdateDTO.builder()
                .empName("박다윤")
                .gender("여성")
                .email("dada22@gmail.com")
                .empPhone("01066984568")
                .empAddress("부산시 해운대구 롯데캐슬 1002동 510호")
                .modUser("tndus")
                .empNo(2L)
                .build();

        log.info(empService.update(empUpdateDTO));
    }

    @Test
    @DisplayName("사원의 프로필 이미지를 삭제합니다.")
    void deleteProfile() {
        log.info(empService.deleteProfile(20L));
    }


    @Test
    @DisplayName("사원 번호로 사원의 조직 정보를 조회합니다.")
    void findOrgaById() {
        log.info(empService.findOrgaById(2L));
    }

    @Test
    @DisplayName("사원의 조직 정보를 추가합니다.")
    void empOrgaInsert() {
        Long empNo = 2L;

        EmpOrgaDTO orgaDTO = EmpOrgaDTO.builder()
                .deptNo(5L)
                .regUser("tndus")
                .build();

        Long orgaNo = orgaDTO.getOrgaNo();

        EmpMappingDTO empMappingDTO = EmpMappingDTO.builder()
                .empNo(empNo)
                .orgaNo(orgaNo)
                .empRank("부장")
                .regUser("tndus")
                .build();

        log.info(empService.empOrgaInsert(orgaDTO, empMappingDTO, empNo));
    }

    @Test
    @DisplayName("로그인한 사용자 아이디로 모든 회사, 부서, 사원 정보를 조회합니다.")
    void findLoginUser() {
        log.info(empService.findLoginUser("darin111"));
    }
}
