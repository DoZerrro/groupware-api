package com.dq.aquaranth.rolegroup.mapper;

import com.dq.aquaranth.rolegroup.domain.MenuRole;
import com.dq.aquaranth.rolegroup.dto.MenuRoleLnbDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 권한그룹 DB 접근을 위한 Mapper.
 *
 * @author 임종현
 */
public interface MenuRoleMapper {

    List<MenuRole> findAllByRoleGroupNo(Long roleGroupNo);

    /**
     * 권한그룹과, 상위모듈에 대한 메뉴권한을 조회합니다.
     *
     * @param roleGroupNo 메뉴권한이 부여된 권한그룹
     * @param moduleCode GNB 메뉴코드
     * @return 권한그룹에 대한 메뉴권한이 체크된 LNB 객체 리스트
     */
    List<MenuRoleLnbDTO> findByRoleGroupNoAndModuleCode(@Param("roleGroupNo") Long roleGroupNo, @Param("moduleCode") String moduleCode);

    void insert(MenuRole menuRole);

    Integer deleteAllByRoleGroupNo(Long roleGroupNo);

    /**
     *
     * @param roleGroupNo
     * @param moduleCode
     */
    void deleteByRoleGroupNoAndModuleCode(@Param("roleGroupNo") Long roleGroupNo, @Param("moduleCode") String moduleCode);

}
