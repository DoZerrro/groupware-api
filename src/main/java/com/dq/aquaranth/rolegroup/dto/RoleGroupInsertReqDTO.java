package com.dq.aquaranth.rolegroup.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoleGroupInsertReqDTO {
    private Long companyNo;
    private String roleGroupName;
    private boolean roleGroupUse;
}
