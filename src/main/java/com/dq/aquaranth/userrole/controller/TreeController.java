package com.dq.aquaranth.userrole.controller;

import com.dq.aquaranth.menu.annotation.MenuCode;
import com.dq.aquaranth.menu.constant.MenuCodes;
import com.dq.aquaranth.userrole.dto.tree.TreeOrgaListDTO;
import com.dq.aquaranth.userrole.dto.tree.TreeReqOrgaListDTO;
import com.dq.aquaranth.userrole.service.TreeService;
import com.dq.aquaranth.userrole.dto.tree.TreeDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/api/orgaTree")
@MenuCode(MenuCodes.ROLE0020)
public class TreeController {

    private final TreeService treeService;

    @GetMapping("/list/{upperDeptNo}/{depth}/{companyNo}")
    public List<TreeDTO> listDept2 (@PathVariable("upperDeptNo") Long upperDeptNo, @PathVariable("depth") int depth, @PathVariable("companyNo") Long companyNo) {

        return treeService.listDepth(upperDeptNo, depth, companyNo);
    }

    @GetMapping("/orgaList")
    public List<TreeOrgaListDTO> findOrgaList(TreeReqOrgaListDTO treeReqOrgaListDTO){
        log.info("findOrgaList");
        log.info(treeReqOrgaListDTO);
        return treeService.findOrgaList(treeReqOrgaListDTO);
    }
}
