package com.dq.aquaranth.userrole.mapper;

import com.dq.aquaranth.userrole.dto.tree.TreeDTO;
import com.dq.aquaranth.userrole.dto.tree.TreeOrgaListDTO;
import com.dq.aquaranth.userrole.dto.tree.TreeReqOrgaListDTO;

import java.util.List;

public interface TreeMapper {
    List<TreeDTO> getFromParent(Long upperDeptNo, int depth, Long companyNo);

    List<TreeOrgaListDTO> findOrgaList(TreeReqOrgaListDTO treeReqOrgaListDTO);
}
