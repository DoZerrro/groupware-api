package com.dq.aquaranth.rolegroup.controller;

import com.dq.aquaranth.menu.annotation.MenuCode;
import com.dq.aquaranth.menu.constant.MenuCodes;
import com.dq.aquaranth.rolegroup.domain.RoleGroup;
import com.dq.aquaranth.rolegroup.dto.*;
import com.dq.aquaranth.rolegroup.service.RoleGroupService;
import com.dq.aquaranth.userrole.dto.response.UserRoleCompanyDTO;
import com.dq.aquaranth.userrole.service.UserRoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/role-group")
@Log4j2
@MenuCode(MenuCodes.ROLE0010)
public class RoleGroupApiController {
    private final RoleGroupService roleGroupService;
    private final UserRoleService userRoleService;


    @GetMapping("")
    public PageResponseDTO<RoleGroupResponseDTO> getRoleGroupList(PageRequestDTO pageRequestDTO) {
        return roleGroupService.getList(pageRequestDTO);
    }

    @GetMapping("/{roleGroupNo}")
    public RoleGroup getRoleGroup(@PathVariable Long roleGroupNo) {
        return roleGroupService.findById(roleGroupNo);
    }

    @PostMapping("")
    public RoleGroup register(@RequestBody RoleGroupInsertReqDTO reqDTO, Authentication authentication) {
        RoleGroup insertRoleGroup = RoleGroup.builder()
                .companyNo(reqDTO.getCompanyNo())
                .roleGroupName(reqDTO.getRoleGroupName())
                .roleGroupUse(reqDTO.isRoleGroupUse())
                .regUser(authentication.getName())
                .regDate(LocalDateTime.now())
                .build();

        return roleGroupService.insert(insertRoleGroup);
    }

    @PutMapping("")
    public void modify(@RequestBody RoleGroupUpdateReqDTO reqDTO, Authentication authentication) {
        RoleGroupUpdateDTO updateDTO = RoleGroupUpdateDTO.builder()
                .roleGroupNo(reqDTO.getRoleGroupNo())
                .roleGroupName(reqDTO.getRoleGroupName())
                .roleGroupUse(reqDTO.isRoleGroupUse())
                .companyNo(reqDTO.getCompanyNo())
                .modUser(authentication.getName())
                .modDate(LocalDateTime.now())
                .build();

        roleGroupService.update(updateDTO);
    }

    @DeleteMapping("/{roleGroupNo}")
    public void remove(@PathVariable Long roleGroupNo) {
        roleGroupService.deleteById(roleGroupNo);
    }

    @GetMapping("/companyListAll")
    public List<UserRoleCompanyDTO> findCompanyAll() {
        return userRoleService.findCompanyAll();
    }
}
