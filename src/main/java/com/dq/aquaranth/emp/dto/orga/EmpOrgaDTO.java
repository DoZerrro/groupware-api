package com.dq.aquaranth.emp.dto.orga;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * 사원의 회사, 부서(조직) DTO 입니다.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmpOrgaDTO {
    private Long orgaNo;
    private Long upperOrgaNo;
    private Long deptNo;
    private String orgaType;
    private String regUser;
    private String modUser;
    private LocalDate regDate, modDate;
}
