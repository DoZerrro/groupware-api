package com.dq.aquaranth.emp.mapper;

import com.dq.aquaranth.emp.dto.emp.EmpMappingDTO;

public interface EmpMappingMapper {
    /**
     * 사원에 대한 조직 정보를 알기 위해
     * 사원-조직 매핑 테이블에
     * 사원 정보와 조직 정보를 추가합니다.
     *
     * @param empMappingDTO : 사원-조직 매핑 정보
     */
    Long empMappingInsert(EmpMappingDTO empMappingDTO);

    // 레디스에 올릴 empMapping값. username과 deptno으로 검색한다.

    /**
     * 로그인한 아이디와 접속 시 선택한 부서 번호로
     * Redis에 등록될 사원-조직 정보를 등록합니다.
     *
     * @param username : 로그인한 사원 아이디
     * @param deptNo : 선택한 부서 번호
     */
    EmpMappingDTO findByUsernameAndDeptNo(String username, Long deptNo);

    /**
     * 사원을 미사용으로 체크했을 때, 속한 모든 조직의 퇴사일을 생성합니다.
     *
     * @param empNo : 미사용으로 변경된 사원 번호
     */
    Long updateEmpMappingRetiredDate(Long empNo);
}
