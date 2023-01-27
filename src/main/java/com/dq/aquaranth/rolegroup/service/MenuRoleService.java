package com.dq.aquaranth.rolegroup.service;

import com.dq.aquaranth.rolegroup.domain.MenuRole;
import com.dq.aquaranth.rolegroup.dto.MenuRoleLnbDTO;

import java.util.List;


/**
 * 권한그룹에 각 메뉴에 대한 권한을 부여하는 비즈니스 로직을 처리합니다.
 *
 * @author 임종현
 */
public interface MenuRoleService {

    /**
     * 권한그룹에 부여된 메뉴권한들의 목록을 불러옵니다.
     * 단, 선택된 하나의 상위메뉴를 포함한 하위메뉴에 대한 메뉴권한만 불러옵니다.
     *
     * @param roleGroupNo - 부여된 메뉴권한을 찾을 권한그룹번호
     * @param moduleCode - 구별할 상위메뉴코드
     *
     * @return - 메뉴번호와 권한여부를 포함한 객체
     */
    List<MenuRoleLnbDTO> findByRoleGroupNoAndModuleCode(Long roleGroupNo, String moduleCode);

    /**
     * 메뉴권한 추가를 요청합니다.
     *
     * @param insertMenuRoles - 추가할 메뉴권한 객체 리스트
     * @return - 추가된 메뉴권한 객체 리스트
     */
    List<MenuRole> insert(List<MenuRole> insertMenuRoles);

    /**
     * 권한그룹번호에 대한 모든 메뉴권한 삭제를 요청합니다.
     * @param roleGroupNo - 삭제할 메뉴권한번호
     */
    void deleteByRoleGroupNo(Long roleGroupNo);

    /**
     * 권한그룹에 설정된 모든 메뉴권한을 삭제하고, 요청받은 메뉴번호에 대한 권한을 새로 부여합니다.
     * 단, 상위모듈 포함 하위메뉴들에 대한 메뉴들만 해당됩니다.
     *
     * @param insertMenuRoles - 부여할 메뉴권한 리스트
     * @param moduleCode - 부여할 메뉴권한의 상위모듈
     * @param roleGroupNo - 부여받은 권한그룹 번호
     */
    void save(List<MenuRole> insertMenuRoles, String moduleCode, Long roleGroupNo);
}
