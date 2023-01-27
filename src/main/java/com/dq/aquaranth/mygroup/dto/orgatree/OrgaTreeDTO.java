package com.dq.aquaranth.mygroup.dto.orgatree;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrgaTreeDTO {
    private Long deptNo, upperDeptNo, orgaNo, companyNo;
    private String deptName;
    private Long ord, depth, lastDno;
}
