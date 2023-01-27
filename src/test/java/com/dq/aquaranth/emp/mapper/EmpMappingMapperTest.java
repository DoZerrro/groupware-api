package com.dq.aquaranth.emp.mapper;

import com.dq.aquaranth.emp.dto.emp.EmpMappingDTO;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Log4j2
class EmpMappingMapperTest {

    @Autowired(required = false)
    private EmpMappingMapper empMappingMapper;

    @Test
    @DisplayName("사원 조직 매핑 테이블을 추가합니다.")
    void empMappingInsert() {
        EmpMappingDTO empMappingDTO = EmpMappingDTO
                .builder()
                .orgaNo(11L)
                .empNo(1L)
                .empRank("사원")
                .regUser("tndus")
                .build();

        log.info(empMappingMapper.empMappingInsert(empMappingDTO));
    }

    @Test
    @DisplayName("퇴사한 사원의 번호를 받아 모든 조직에 퇴사일을 수정합니다.")
    void updateEmpMappingRetiredDate() {
        log.info(empMappingMapper.updateEmpMappingRetiredDate(1L));
    }
}
