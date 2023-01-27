package com.dq.aquaranth.rolegroup.service.impl;

import com.dq.aquaranth.login.annotation.RedisUpdate;
import com.dq.aquaranth.rolegroup.domain.RoleGroup;
import com.dq.aquaranth.rolegroup.dto.PageRequestDTO;
import com.dq.aquaranth.rolegroup.dto.PageResponseDTO;
import com.dq.aquaranth.rolegroup.dto.RoleGroupResponseDTO;
import com.dq.aquaranth.rolegroup.dto.RoleGroupUpdateDTO;
import com.dq.aquaranth.rolegroup.mapper.MenuRoleMapper;
import com.dq.aquaranth.rolegroup.mapper.RoleGroupMapper;
import com.dq.aquaranth.rolegroup.service.RoleGroupService;
import com.dq.aquaranth.userrole.mapper.UserRoleMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Log4j2
public class RoleGroupServiceImpl implements RoleGroupService {
    private final RoleGroupMapper roleGroupMapper;
    private final UserRoleMapper userRoleMapper;
    private final MenuRoleMapper menuRoleMapper;
    @Override
    public List<RoleGroupResponseDTO> findAll() {
        log.info("권한그룹을 조회합니다");
        return roleGroupMapper.findAll();
    }

    @Override
    public RoleGroup findById(Long roleGroupNo) {
        log.info("{} 번째 권한그룹을 조회합니다", roleGroupNo);
        return roleGroupMapper.findById(roleGroupNo);
    }

    @Override
    public List<RoleGroup> findByMenuCode(String menuCode) {
        return roleGroupMapper.findByMenuCode(menuCode);
    }

    @Override
    public void update(RoleGroupUpdateDTO updateDTO) {
        log.info("{} 번째 권한그룹을 수정합니다.", updateDTO.getRoleGroupNo());
        roleGroupMapper.update(updateDTO);
    }

    @Override
    public RoleGroup insert(RoleGroup insertDTO) {
        Long result = roleGroupMapper.insert(insertDTO);
        log.info("권한그룹을 등록합니다. 등록된 권한그룹번호 => {}", insertDTO.getRoleGroupNo());

        if (result < 1) {
            log.error("insert가 정상적으로 이루어지지 않았습니다.");
        }

        return insertDTO;
    }

    @Transactional
    @Override
    @RedisUpdate
    public void deleteById(Long roleGroupNo) {
        log.info("{} 번째 권한그룹을 삭제합니다.", roleGroupNo);

        RoleGroup findDTO = roleGroupMapper.findById(roleGroupNo);

        if (Objects.isNull(findDTO)) {
            log.error("삭제하려는 권한그룹은 존재하지 않습니다");
            throw new RuntimeException("삭제하려는 권한그룹은 존재하지 않습니다");
        }

        // 연관 테이블 선행 삭제
        userRoleMapper.deleteAllByRoleGroupNo(roleGroupNo);
        menuRoleMapper.deleteAllByRoleGroupNo(roleGroupNo);

        roleGroupMapper.deleteById(roleGroupNo);
        log.info("권한그룹을 삭제가 완료되었습니다.");
    }

    @Override
    public void hideById(Long roleGroupNo) {
        log.info("{} 번째 권한그룹을 숨김처리 합니다.", roleGroupNo);

        RoleGroup findDTO = roleGroupMapper.findById(roleGroupNo);

        if (Objects.isNull(findDTO)) {
            log.error("숨김처리 하려는 권한그룹은 존재하지 않습니다");
            throw new RuntimeException("숨김처리 하려는 권한그룹은 존재하지 않습니다");
        }

        roleGroupMapper.hideById(roleGroupNo);
        log.info("{}번 권한그룹이 숨김처리 되었습니다.", findDTO.getRoleGroupNo());
    }

    @Override
    public PageResponseDTO<RoleGroupResponseDTO> getList(PageRequestDTO pageRequestDTO) {
        log.info("권한그룹 리스트를 가져옵니다.");
        List<RoleGroupResponseDTO> result = roleGroupMapper.getList(pageRequestDTO);
        int total = roleGroupMapper.getTotal();
        return new PageResponseDTO<>(pageRequestDTO, total, result);
    }
}
