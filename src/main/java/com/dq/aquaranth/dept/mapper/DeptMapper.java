package com.dq.aquaranth.dept.mapper;


import com.dq.aquaranth.dept.dto.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DeptMapper {
    public DeptDTO select(Long deptNo);

    public DeptDTO selectDept(Long deptNo);

    public Long selectDeptOrgaNo(Long deptNo);

    public Long delete(Long deptNo);

    public int update(DeptDTO deptDTO2);

    public List<DeptDTO> getGnoList(Long companyNo);

    //===========================등록 트랜잭션======================================

    Integer getNextOrd(@Param("company") Long company, @Param("parentDeptNo") Long parentDeptNo);

    void arrangeOrd(@Param("company") Long company, @Param("ord") Long ord);

    void fixOrd (@Param("deptNo") Long deptNo, @Param("ord") Long ord);

    void updateLastDno(@Param("parentDeptNo") Long parentDeptNo, @Param("deptNo") Long deptNo);

    public int insert(DeptRegisterDTO deptRegisterDTO);

    int insertOrga(DeptOrgaRegisterDTO deptOrgaRegisterDTO);

    int insertOrgaMapping(DeptMappingRegisterDTO deptMappingRegisterDTO);

    //===========================등록 트랜잭션======================================

    List<DeptDTO> getFromParent(@Param("upperDeptNo") Long upperDeptNo, @Param("depth") int depth);

    //    트리 구조 조회
    List<DeptTreeDTO> getTree(@Param("company") Long company );

    //상위 회사 먼저 나오고 클릭하면 하위 부서 조회
    List<DeptDTO> getSubDepth(GetSubDeptDTO getSubDeptDTO);

    // 검색
    List<DeptSearchDTO> deptSearch(DeptSearchParamDTO deptSearchParamDTO);

    // 부서원 정보 조회
    List<DeptMemberDTO> deptMember(Long orgaNo);


    /**
     * 선택한 회사의 부서 목록을 조회합니다.
     *
     * @param companyNo : 선택된 회사 번호
     */
    List<DepartmentDTO> findByCompanyNo(Long companyNo);

    //종현
    DeptDTO findByUsername(String username);





}
