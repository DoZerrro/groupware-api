package com.dq.aquaranth.dept.service;

import com.dq.aquaranth.dept.dto.DeptRegisterDTO;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Log4j2
class DeptServiceTest {

    @Autowired
    DeptService deptService;

    @Test
    void insert() {
        DeptRegisterDTO deptRegisterDTO = DeptRegisterDTO
                .builder()
                .deptNo(null)
                .deptName("개발팀")
                .deptDesc("개발팀")
                .username("emp10")
                .build();
        log.info(deptRegisterDTO.getDeptNo());
        deptService.insert(deptRegisterDTO);
        log.info(deptRegisterDTO.getDeptNo());
    }
}
