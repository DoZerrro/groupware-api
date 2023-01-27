package com.dq.aquaranth.userrole.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRoleUserListBasedDTO {
    private Long orgaNo, roles;
    private String orgaInfo, companyName, deptName, empRank, username, empName;
}
