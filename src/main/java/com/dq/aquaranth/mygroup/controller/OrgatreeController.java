package com.dq.aquaranth.mygroup.controller;

import com.dq.aquaranth.menu.annotation.MenuCode;
import com.dq.aquaranth.mygroup.dto.orgatree.OrgaTreeDTO;
import com.dq.aquaranth.mygroup.dto.orgatree.OrgaTreeEmpListDTO;
import com.dq.aquaranth.mygroup.dto.orgatree.OrgatreeEmpInformationDTO;
import com.dq.aquaranth.mygroup.service.orgatree.OrgatreeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/api/orgatree")
@MenuCode
public class OrgatreeController {

    private final OrgatreeService orgatreeService;

    /**
     * 조직도
     */
    @GetMapping("/tree/{upperDeptNo}/{depth}/{companyNo}")
    public List<OrgaTreeDTO> getOrgaTree (@PathVariable("upperDeptNo") Long upperDeptNo, @PathVariable("depth") Long depth, @PathVariable("companyNo") Long companyNo) {
        log.info("조직도 트리 출력");
        return orgatreeService.findAllOrga(upperDeptNo, depth, companyNo);
    }

    @GetMapping("/list/{orgaNo}")
    public List<OrgaTreeEmpListDTO> getEmpInfoList(@PathVariable Long orgaNo) {
        log.info("해당 부서에 속한 사원 정보 모두 조회");
        return orgatreeService.findAllEmpInformation(orgaNo);
    }

    @GetMapping("/information/{orgaNo}")
    public OrgatreeEmpInformationDTO getEmpInformation(@PathVariable Long orgaNo) {
        log.info("해당 부서에 소속된 사원 중 한 사원의 정보 조회");
        return orgatreeService.findByEmpNo(orgaNo);
    }

}
