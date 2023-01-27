package com.dq.aquaranth.userrole.dto.tree;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TreeDTO {
    private Long deptNo, upperDeptNo, companyNo, orgaNo;
    private String deptName;
    private int depth, lowerDeptCnt;
}
