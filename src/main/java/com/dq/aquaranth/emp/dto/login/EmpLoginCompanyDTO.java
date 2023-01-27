package com.dq.aquaranth.emp.dto.login;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 현재 로그인한 사원이 속한 모든 회사 정보 DTO 입니다.
 * (접속 변경된 회사, 부서 선택에 따라 변하지 않는 정보)
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class EmpLoginCompanyDTO {
    private Long companyNo;
    private String companyName;

    private List<EmpLoginDepartmentDTO> deptList;
}
