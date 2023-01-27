package com.dq.aquaranth.mygroup.service.orgatree;

import com.dq.aquaranth.mygroup.dto.orgatree.OrgaTreeDTO;
import com.dq.aquaranth.mygroup.dto.orgatree.OrgaTreeEmpListDTO;
import com.dq.aquaranth.mygroup.dto.orgatree.OrgatreeEmpInformationDTO;

import java.util.List;

public interface OrgatreeService {

    /**
     * 조직도
     */
    List<OrgaTreeDTO> findAllOrga (Long upperDeptNo, Long depth, Long companyNo);
    List<OrgaTreeEmpListDTO> findAllEmpInformation(Long orgaNo);
    OrgatreeEmpInformationDTO findByEmpNo(Long orgaNo);
}
