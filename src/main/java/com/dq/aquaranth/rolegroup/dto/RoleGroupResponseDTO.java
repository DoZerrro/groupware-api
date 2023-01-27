package com.dq.aquaranth.rolegroup.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RoleGroupResponseDTO {
    private Long roleGroupNo;
    private String roleGroupName;
    private boolean roleGroupUse;
    private Long companyNo;
    private String regUser;
    //    default
    private LocalDateTime regDate;
    //    null
    private String modUser;
    //    null
    private LocalDateTime modDate;
    private String companyName;
}
