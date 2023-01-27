package com.dq.aquaranth.company.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompanyDeptDTO {

    /**
     * 회사 추가 시 부서 추가할 때 필요한 정보를 담을 DTO
     * 회사번호(추가한 회사번호), 부서번호(Auto Increment) , 부서깊이
     * 부서이름, 부서설명, 등록자(사원의 아이디)
     */
    private Long companyNo, deptNo, depth;
    private String deptName, deptDesc, regUser;
}
