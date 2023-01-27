package com.dq.aquaranth.dept.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DeptTreeDTO {

    private Long deptNo, orgaNo, upperDeptNo, companyNo, lastDno;
    private int ord, depth;
    private String deptName;

}
