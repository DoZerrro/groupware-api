package com.dq.aquaranth.emp.mapper;


import com.dq.aquaranth.emp.dto.emp.EmpDTO;
import com.dq.aquaranth.emp.dto.emp.EmpFileDTO;
import com.dq.aquaranth.emp.dto.orga.EmpOrgaSelectDTO;
import com.dq.aquaranth.emp.dto.emp.EmpUpdateDTO;
import com.dq.aquaranth.emp.dto.login.EmpLoginEmpDTO;
import com.dq.aquaranth.emp.dto.login.EmpUpdateRecentAccessDTO;
import com.dq.aquaranth.emp.dto.orga.EmpOrgaDTO;
import com.dq.aquaranth.emp.dto.orga.EmpOrgaUpdateDTO;

import java.util.List;

public interface EmpMapper {

    // 사원

    /**
     * 모든 사원 정보를 조회합니다.
     *
     * @return : 모든 사원 목록
     */
    List<EmpDTO> findAll();

    /**
     * 선택된 사원 정보를 조회합니다.
     *
     * @param empNo : 선택된 사원 번호
     * @return : 선택된 사원에 대한 모든 정보
     */
    EmpDTO findById(Long empNo);

    /**
     * 새로운 사원을 추가합니다.
     *
     * @param empInsertDTO : 추가될 사원 정보
     */
    Long insert(EmpDTO empInsertDTO);

    /**
     * 사원 정보를 수정합니다.
     *
     * @param empUpdateDTO : 수정할 사원 정보
     */
    Long update(EmpUpdateDTO empUpdateDTO);


    // 사원 회사, 부서(조직)

    /**
     * 선택된 사원의 조직 정보를 조회합니다.
     *
     * @param empNo : 선택된 사원 번호
     * @return : 선택된 사원의 모든 조직 목록
     */
    List<EmpOrgaSelectDTO> findOrgaById(Long empNo);

    /**
     * 사원을 새로운 회사, 부서(조직)에 추가합니다.
     *
     * @param empOrgaInsertDTO : 조직에 추가될 사원 정보
     */
    Long insertOrga(EmpOrgaDTO empOrgaInsertDTO);


    /**
     * 로그인 시 접속한 회사, 부서 정보를 회사부터 최하위 부서까지 찾아 출력합니다.
     *
     * @param orgaNo : 접속한 부서의 조직 번호
     * @return : 접속한 조직의 정보
     */
    String functionHierarchy(Long orgaNo);

    /**
     * 사원의 회사, 부서(조직) 정보를 수정합니다.
     *
     * @param empOrgaUpdateDTO : 수정할 사원의 조직 정보
     */
    Long updateOrga(EmpOrgaUpdateDTO empOrgaUpdateDTO);


    // 로그인하여 접속 중인 사원

    /**
     * 로그인한 사원 아이디(username)로 사원 정보를 찾습니다.
     *
     * @param username : 로그인한 사원 아이디(username)
     */
    EmpDTO findByUsername(String username);

    /**
     * 사원의 최근 로그인 ip, 최근 로그인 시간을 수정합니다.
     *
     * @param updateAccessDTO : 최근 ip, 최근 시간 정보
     */
    Long updateRecentAccessInfo(EmpUpdateRecentAccessDTO updateAccessDTO);

    /**
     * 사원의 프로필 사진을 수정합니다.
     *
     * @param empFileDTO : 프로필로 저정할 파일 정보
     */
    Long updateProfile(EmpFileDTO empFileDTO);

    /**
     * 로그인한 아이디(username)로 사원의 정보를 조회합니다.
     *
     * @param username : 로그인한 사원의 아이디
     */
    List<EmpLoginEmpDTO> findLoginUser(String username);

}
