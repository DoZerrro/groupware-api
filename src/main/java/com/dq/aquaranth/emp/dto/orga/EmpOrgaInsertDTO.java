package com.dq.aquaranth.emp.dto.orga;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

/**
 * 사원을 추가할 때 또는 사원의 회사, 부서를 추가할 때
 * 해당 정보를 조직(Orga)에 추가하기 위한 DTO 입니다.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmpOrgaInsertDTO {
    private Long orgaNo;
    private Long empNo;
    private Long upperOrgaNo;
    @NotBlank
    private Long deptNo;
    @NotBlank
    private String empRank;
    private String empRole;
    private String orgaType;
    private String regUser;
    private LocalDate regDate;
}
