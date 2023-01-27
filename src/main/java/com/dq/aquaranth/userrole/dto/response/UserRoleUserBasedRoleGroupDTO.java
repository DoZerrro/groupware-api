package com.dq.aquaranth.userrole.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRoleUserBasedRoleGroupDTO {
    private Long orgaNo, orgaRoleNo,  roleGroupNo, curOrgaNo, companyNo, comOrgaNo;
    private String roleGroupName, companyName;
}
