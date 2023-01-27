package com.dq.aquaranth.userrole.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRoleRoleGroupBasedListDTO {
    private Long roleGroupNo, companyNo, orgaNo;
    private String roleGroupName, companyName;
}
