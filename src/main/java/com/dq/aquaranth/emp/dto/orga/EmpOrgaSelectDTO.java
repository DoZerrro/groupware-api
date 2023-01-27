package com.dq.aquaranth.emp.dto.orga;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * 사원이 속한 모든 회사, 부서, 사원 정보 DTO 입니다.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmpOrgaSelectDTO {
    private Long empNo;
    private Long orgaNo;
    private Long companyNo;
    private Long deptNo;
    private String companyName;
    private String deptName;
    private String empRank;
    private String companyTel;
    private String companyAddress;
    private String empRole;
    private boolean deptMain;
    private LocalDate hiredDate, retiredDate;
}
