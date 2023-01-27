package com.dq.aquaranth.userrole.dto.tree;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TreeOrgaListDTO {
    private Long orgaNo, upperOrgaNo;
    private String companyName, deptName, empRank, username, empName, orgaType;
}
