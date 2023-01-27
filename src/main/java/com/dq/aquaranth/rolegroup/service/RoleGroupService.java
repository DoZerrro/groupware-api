package com.dq.aquaranth.rolegroup.service;

import com.dq.aquaranth.rolegroup.domain.RoleGroup;
import com.dq.aquaranth.rolegroup.dto.PageRequestDTO;
import com.dq.aquaranth.rolegroup.dto.PageResponseDTO;
import com.dq.aquaranth.rolegroup.dto.RoleGroupResponseDTO;
import com.dq.aquaranth.rolegroup.dto.RoleGroupUpdateDTO;

import java.util.List;

/**
 * 권한그룹 비즈니스 로직을 처리합니다.
 *
 * @author 임종현
 */
public interface RoleGroupService {

    /**
     * 권한그룹 리스트를 요청합니다.
     *
     * @return tbl_role_group 테이블에서, companyName 컬럼을 추가한 객체를 반환
     */
    List<RoleGroupResponseDTO> findAll();

    /**
     * 권한그룹 번호로 권한그룹을 요청합니다.
     *
     * @param roleGroupNo - 권한그룹번호 (pk)
     * @return 유효한 사용자의 프로필 정보
     */
    RoleGroup findById(Long roleGroupNo);

    List<RoleGroup> findByMenuCode(String menuCode);

    /**
     * 권한그룹 내용수정을 요청합니다.
     *
     * @param updateDTO - 수정할 권한그룹 객체
     */
    void update(RoleGroupUpdateDTO updateDTO);

    /**
     * 권한그룹 등록을 요청합니다.
     *
     * @param insertDTO - 등록할 권한그룹, 권한그룹번호는 auto_increment 이기 때문에 비어있습니다.
     * @return 등록된 권한그룹번호를 포함한 권한그룹 객체
     */
    RoleGroup insert(RoleGroup insertDTO);

    /**
     * 권한그룹 번호로 권한그룹 삭제를 요청합니다.
     *
     * @param roleGroupNo - 권한그룹번호 (pk)
     */
    void deleteById(Long roleGroupNo);

    /**
     * 권한그룹 번호로 role_group_use 를 false 로 요청합니다
     * delete 문에 제약조건이 복잡하게 걸려있을 경우 사용합니다.
     *
     * @param roleGroupNo - 권한그룹번호 (pk)
     */
    void hideById(Long roleGroupNo);

    /**
     * 선택된 페이지번호와 사이즈 만큼 권한그룹 리스트를 보여줍니다.
     *
     * @param pageRequestDTO 페이징 정보와, 검색할 키워드가 담긴 객체
     * @return 페이징 정보가 담긴 객체
     */
    PageResponseDTO<RoleGroupResponseDTO> getList(PageRequestDTO pageRequestDTO);
}
