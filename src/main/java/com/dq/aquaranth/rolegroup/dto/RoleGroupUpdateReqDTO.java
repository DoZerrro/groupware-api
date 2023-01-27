package com.dq.aquaranth.rolegroup.dto;

import lombok.Data;

@Data
public class RoleGroupUpdateReqDTO {
    private Long roleGroupNo;
    private Long companyNo;
    private String roleGroupName;
    private boolean roleGroupUse;
}
