package com.dq.aquaranth.rolegroup.mapper;

import com.dq.aquaranth.login.domain.LoginUser;
import com.dq.aquaranth.rolegroup.domain.RoleGroup;
import com.dq.aquaranth.rolegroup.dto.PageRequestDTO;
import com.dq.aquaranth.rolegroup.dto.RoleGroupResponseDTO;
import com.dq.aquaranth.rolegroup.dto.RoleGroupUpdateDTO;
import com.dq.aquaranth.userrole.dto.paging.PageResponseDTO;

import java.util.List;

/**
 * 권한그룹 DB 접근을 위한 Mapper.
 *
 * @author 임종현
 */
public interface RoleGroupMapper {

    /**
     * 권한그룹들을 조회합니다.
     */
    List<RoleGroupResponseDTO> findAll();

    /**
     * 권한그룹번호로 권한그룹들을 조회합니다.
     *
     * @param roleGroupNo : 찾으려는 권한그룹번호입니다.
     * @return 찾은 권한그룹을 전부 반환합니다.
     */
    RoleGroup findById(Long roleGroupNo);

    /**
     * 권한그룹 등록
     *
     * @param roleGroup : 등록할 권한그룹 객체
     */
    Long insert(RoleGroup roleGroup);

    /**
     * 권한그룹번호로 권한그룹을 수정합니다.
     * @param updateDTO : 수정할 권한그룹 객체
     */
    void update(RoleGroupUpdateDTO updateDTO);

    /**
     * 권한그룹번호로 권한그룹을 삭제합니다.
     * @param roleGroupNo : 삭제할 권한그룹번호
     */
    void deleteById(Long roleGroupNo);

    /**
     * 권한그룹번호로 권한그룹을 숨김처리합니다.
     * @param roleGroupNo : 숨길 권한그룹번호
     */
    void hideById(Long roleGroupNo);

    /**
     * 로그인한 사원의 권한그룹을 가져옵니다.
     * @param loginUser 로그인한 사원의 정보
     * @return 권한그룹 리스트
     */
    List<RoleGroup> findRoleGroupsByLoginUser(LoginUser loginUser);

    /**
     * 단일메뉴에 대한 권한이 부여된 권한그룹들을 요청합니다.
     *
     * @param menuCode 부여된 메뉴코드
     * @return 권한그룹 리스트
     */
    List<RoleGroup> findByMenuCode(String menuCode);

    /**
     * request parameter 여부에 따라 동적으로 권한그룹들을 요청합니다.
     * @param reqDTO 회사번호, 권한그룹명이 들어간 요청객체
     * @return 권한그룹 리스트
     */
    List<RoleGroupResponseDTO> getList(PageRequestDTO reqDTO);

    int getTotal();
}
