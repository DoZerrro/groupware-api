package com.dq.aquaranth.login.dto;

import com.dq.aquaranth.company.dto.CompanyInformationDTO;
import com.dq.aquaranth.dept.dto.DeptDTO;
import com.dq.aquaranth.emp.dto.emp.EmpDTO;
import com.dq.aquaranth.rolegroup.domain.RoleGroup;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class RedisDTO {
    private CompanyInformationDTO company;
    private DeptDTO dept;
    private EmpDTO emp;
    private List<RoleGroup> roleGroups;
}
