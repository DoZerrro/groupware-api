package com.dq.aquaranth.emp.dto.login;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 접속회사 선택 또는 변경 시,
 * Redis에 있는 정보를 가져오기 위한 DTO 입니다.
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class EmpRedisDTO {
    private Long loginCompany;
    private String loginCompanyName;
    private Long loginDept;
    private String loginDeptName;
    private String loginEmpRank;
    private String hierarchy;
}
