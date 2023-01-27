package com.dq.aquaranth.mygroup.mapper;

import com.dq.aquaranth.mygroup.dto.orgatree.OrgaTreeDTO;
import com.dq.aquaranth.mygroup.dto.orgatree.OrgaTreeEmpListDTO;
import com.dq.aquaranth.mygroup.dto.orgatree.OrgatreeEmpInformationDTO;

import java.util.List;

public interface OrgatreeMapper {

    /**
     * 조직도 트리 출력
     */
    List<OrgaTreeDTO> findAllOrga(Long upperDeptNo, Long depth, Long companyNo);

    /**
     * 해당 부서에 속한 사원 정보 모두 조회
     */
    List<OrgaTreeEmpListDTO> findAllEmpInformation(Long orgaNo);

    /**
     * 해당 부서에 소속된 사원 중 한 사원의 정보 조회
     */
    OrgatreeEmpInformationDTO findByEmpNo(Long orgaNo);
}
