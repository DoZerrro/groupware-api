package com.dq.aquaranth.emp.dto.login;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 현재 로그인한 사원이 속한 모든 부서 정보 DTO 입니다.
 * (접속 변경된 회사, 부서 선택에 따라 변하지 않는 정보)
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class EmpLoginDepartmentDTO {
    private Long deptNo;
    private Long orgaNo;
    private String deptName;
    private String empRank;
    private String info;
}
