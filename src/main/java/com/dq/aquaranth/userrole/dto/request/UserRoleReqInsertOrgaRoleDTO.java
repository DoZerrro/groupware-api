package com.dq.aquaranth.userrole.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRoleReqInsertOrgaRoleDTO {
    private Long roleGroupNo, orgaNo;
    private List<Long> orgaNoList;
    private String username;
}
