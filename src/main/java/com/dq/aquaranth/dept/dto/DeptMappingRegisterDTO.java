package com.dq.aquaranth.dept.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeptMappingRegisterDTO {

    /**
     * 부서번호, 조직번호, 등록자
     */

    private Long deptNo, orgaNo;
    private String regUser;
}
