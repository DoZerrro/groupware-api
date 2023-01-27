package com.dq.aquaranth.rolegroup.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoleGroupUpdateDTO {
    private Long roleGroupNo;
    private String roleGroupName;
    private boolean roleGroupUse;
    private Long companyNo;
    private String modUser;
    private LocalDateTime modDate;
}
