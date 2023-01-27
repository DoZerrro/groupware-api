package com.dq.aquaranth.emp.mapper;

import com.dq.aquaranth.emp.dto.emp.EmpDTO;
import com.dq.aquaranth.emp.dto.emp.EmpUpdateDTO;
import com.dq.aquaranth.emp.dto.orga.EmpOrgaDTO;
import com.dq.aquaranth.emp.dto.orga.EmpOrgaUpdateDTO;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;

@SpringBootTest
@Log4j2
class EmpMapperTest {
    @Autowired(required = false)
    private EmpMapper empMapper;
    @Autowired(required = false)
    PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("사원의 조직 정보를 추가합니다.")
    void insertOrga() {
        EmpOrgaDTO empOrgaInsertDTO = EmpOrgaDTO.builder()
                .deptNo(5L)
                .regUser("tndus")
                .build();

        log.info(empMapper.insertOrga(empOrgaInsertDTO));
    }

    @Test
    @DisplayName("사원 정보를 추가합니다.")
    void insert() {
        EmpDTO empDTO = EmpDTO.builder()
                .empName("이시험")
                .username("emp00")
                .password(passwordEncoder.encode("emp"))
                .regUser("tndus")
                .gender("남성")
                .firstHiredDate(LocalDate.now())
                .build();

        log.info(empMapper.insert(empDTO));
    }

    @Test
    @DisplayName("모든 사원의 목록을 조회합니다.")
    void findAll() {
        log.info(empMapper.findAll());
    }

    @Test
    @DisplayName("사원 번호로 사원을 조회합니다.")
    void findById() {
        log.info(empMapper.findById(1L));
    }

    @Test
    @DisplayName("사원 정보를 수정합니다.")
    void update() {
        EmpUpdateDTO empUpdateDTO = EmpUpdateDTO.builder()
                .empName("이수정")
                .gender("여성")
                .email("editjeong@gmail.com")
                .modUser("tndus")
                .empNo(1L)
                .build();

        log.info(empMapper.update(empUpdateDTO));
    }

    @Test
    @DisplayName("사원 번호로 사원의 조직 정보를 조회합니다.")
    void findOrgaById() {
        log.info(empMapper.findOrgaById(1L));
    }

    @Test
    @DisplayName("사원의 조직 번호로 사원이 속한 회사, 부서 정보를 조회합니다.")
    void functionHierarchy() {
        log.info(empMapper.functionHierarchy(1L));
    }

    @Test
    @DisplayName("조직 정보를 수정합니다.")
    void updateOrga() {
        EmpOrgaUpdateDTO empOrgaUpdateDTO = EmpOrgaUpdateDTO.builder()
                .empRank("인턴")
                .modUser("tndus")
                .deptNo(7L)
                .orgaNo(1L)
                .build();

        log.info(empMapper.updateOrga(empOrgaUpdateDTO));
    }

    @Test
    @DisplayName("로그인한 사용자 아이디로 사원 정보를 조회합니다.")
    void findByUsername() {
        log.info(empMapper.findByUsername("emp00"));
    }

    @Test
    @DisplayName("로그인한 사용자 아이디로 모든 회사, 부서, 사원 정보를 조회합니다.")
    void findLoginUser() {
        log.info(empMapper.findLoginUser("emp00"));
    }
}
