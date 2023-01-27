package com.dq.aquaranth.dept.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeptRegisterDTO {
    /**
     * 등록시 필요한 정보
     */
    private String deptName, deptDesc, username;
    private Long upperDeptNo, companyNo, deptNo, ord, lastDno, depth;
}

