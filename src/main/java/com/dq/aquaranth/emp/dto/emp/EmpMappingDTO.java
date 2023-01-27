package com.dq.aquaranth.emp.dto.emp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * 사원 조직 매핑 DTO 입니다.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmpMappingDTO {
    private Long orgaNo;
    private Long empNo;
    private String empRole;
    private String empRank;
    private String regUser;
    private String modUser;
    private LocalDate hiredDate;
    private LocalDate retiredDate;
    private LocalDate regDate;
    private LocalDate modDate;
}
