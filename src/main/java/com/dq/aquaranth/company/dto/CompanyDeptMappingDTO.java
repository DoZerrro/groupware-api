package com.dq.aquaranth.company.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompanyDeptMappingDTO {

    /**
     * 회사 등록 시 부서 매핑 할 때 필요한 정보를 담을 DTO
     * 부사번호, 조직번호, 등록자(사원의 아이디)
     */
   private Long deptNo, orgaNo;
   private String regUser;
}
