package com.dq.aquaranth.rolegroup.dto;

import lombok.Data;

import java.util.List;

@Data
public class MenuRoleInsertReqDTO {
    private Long roleGroupNo;
    private String moduleCode;
    List<Long> menuRoles;
}
