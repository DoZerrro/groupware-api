package com.dq.aquaranth.userrole.service;

import com.dq.aquaranth.userrole.dto.tree.TreeOrgaListDTO;
import com.dq.aquaranth.userrole.dto.tree.TreeReqOrgaListDTO;
import com.dq.aquaranth.userrole.mapper.TreeMapper;
import com.dq.aquaranth.userrole.dto.tree.TreeDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TreeService {

    private final TreeMapper treeMapper;

    public List<TreeDTO> listDepth (Long upperDeptNo, int depth, Long companyNo){
        return treeMapper.getFromParent(upperDeptNo, depth, companyNo);
    }

    public List<TreeOrgaListDTO> findOrgaList(TreeReqOrgaListDTO treeReqOrgaListDTO){
        return treeMapper.findOrgaList(treeReqOrgaListDTO);
    }
}
