

package com.dq.aquaranth.dept.mapper;


import com.dq.aquaranth.dept.dto.*;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import java.util.List;

@SpringBootTest
@Log4j2
public class DeptMapperTests {

    @Autowired(required = false)
    private DeptMapper deptMapper;

    @Test
    public void testSelect() {
        DeptDTO selectOne = deptMapper.select(14L);
        log.info(selectOne);
    }

    @Test
    void testDelete() {
        deptMapper.delete(44L);
    }

    @Test
    void testUpdate() {
        DeptDTO deptDTO2 = DeptDTO
                .builder()
                .deptNo(23L)
                .upperDeptNo(2L)
                .deptName("철강 1팀 업데이트")
                .delFlag(false)
                .mainFlag(false)
                .deptDesc("철강 1팀 업데이트")
                .build();

        int result = deptMapper.update(deptDTO2);
        log.info("update : " + result);
    }

    @Test
        //@Transactional(rollbackFor = RuntimeException.class)
    void testInsert2() {

        DeptRegisterDTO deptDTO2 = DeptRegisterDTO.builder()
                .deptName("개발1-1팀")
                .deptDesc("개발1-1팀")
                .upperDeptNo(6L)
                .build();

        Long orga = null;

        log.info("----------------------------------");
        log.info("----------------------------------");
        log.info("orga: " + orga);
        log.info("----------------------------------");
        log.info("----------------------------------");

        deptMapper.insert(deptDTO2);

        log.info("================================================");
        log.info(deptDTO2);

        Long newDeptNo = deptDTO2.getDeptNo();

        log.info("newDeptNo " + newDeptNo);
    }

    @Test
    void testSub1() {

        //Long ord = deptMapper.getNextOrd(1L, Long.valueOf(6));

        // log.info("ORD " + ord); //2

    }

    @Test
    void testSub2() {

        Long ord = 3L;
        Long companyNo = 1L;

        deptMapper.arrangeOrd(companyNo, ord);


    }

    @Test
    void testSub3() {

        Long ord = 3L;
        Long deptNo = 22L;

        deptMapper.fixOrd(deptNo,ord);

    }

    @Test
    void testSub4() {

        //#{lastDno} where deptNo = #{parentDeptNo}

        Long lastDno = 22L;
        Long parentDeptNo = 6L;

        deptMapper.updateLastDno(parentDeptNo, lastDno);

    }

    @Test
    @DisplayName("부서 번호, 부서 이름, 회사 번호로 검색 test")
    void search() {
        String testDeptName = "개발";
        Long testCompanyNo = 4L;

        DeptSearchParamDTO deptSearchParamDTO = DeptSearchParamDTO
                .builder()
                .deptSearch(testDeptName)
                .companyNo(testCompanyNo)
                .build();

        List<DeptSearchDTO> deptSearchDTO = deptMapper.deptSearch(deptSearchParamDTO);

        deptSearchDTO.forEach(log::info);
    }

    @Test
    @DisplayName("부서에 맞는 부서원 정보 조회")
    void deptMember() {
        Long orgaNo = 24L;

        List<DeptMemberDTO> list = deptMapper.deptMember(orgaNo);
        list.forEach(log::info);
    }


    public void findByCompanyNoTest(){
        log.info(deptMapper.findByCompanyNo(1L));
    }

}
