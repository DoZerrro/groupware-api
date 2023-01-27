package com.dq.aquaranth.emp.service;

import com.dq.aquaranth.emp.dto.emp.EmpDTO;
import com.dq.aquaranth.emp.dto.emp.EmpMappingDTO;
import com.dq.aquaranth.emp.dto.orga.EmpOrgaSelectDTO;
import com.dq.aquaranth.emp.dto.emp.EmpUpdateDTO;
import com.dq.aquaranth.emp.dto.login.EmpRedisDTO;
import com.dq.aquaranth.emp.dto.login.EmpLoginEmpDTO;
import com.dq.aquaranth.emp.dto.orga.EmpOrgaDTO;
import com.dq.aquaranth.emp.dto.orga.EmpOrgaUpdateListDTO;
import com.dq.aquaranth.menu.objectstorage.dto.request.MultipartFileDTO;

import java.util.List;

public interface EmpService {

    // 사원

    /**
     * 모든 사원의 목록을 조회합니다.
     *
     * 사원의 프로필 사진은
     * uuid와 filename 두 값을 이용하여
     * objectStorageService로 프로필 Url을 완성해 반환합니다.
     */
    List<EmpDTO> findAll();

    /**
     * 선택된 사원의 모든 정보를 조회합니다.
     *
     * 사원의 프로필 사진은
     * uuid와 filename 두 값을 이용하여
     * objectStorageService로 프로필 Url을 완성해 반환합니다.
     *
     * @param empNo : 선택된 사원 번호
     */
    EmpDTO findById(Long empNo);

    /**
     * 사원을 추가합니다.
     *
     * 한 사원이 추가 될 때,
     * 조직, 사원, 사원매핑 총 3개의 정보가 각각 추가됩니다.
     *
     * @param orgaReqDTO : 조직 정보
     * @param reqDTO : 사원 정보
     * @param mapperReqDTO : 사원-조직 매핑 정보
     */
    EmpDTO insert(EmpOrgaDTO orgaReqDTO, EmpDTO reqDTO, EmpMappingDTO mapperReqDTO) throws IllegalAccessException;

    /**
     * 사원 정보를 수정합니다.
     *
     * 퇴사일을 입력하고 수정할 시,
     * 해당 사원의 사용 여부를 미사용으로 업데이트합니다.
     *
     * @param empUpdateDTO : 수정할 사원 정보
     */
    Long update(EmpUpdateDTO empUpdateDTO);

    /**
     * 사원의 프로필을 업데이트합니다.
     *
     * 프로필 등록, 수정 시 파일 서버에 업데이트됩니다.
     *
     * @param fileDto : 이미지 정보
     */
    Long updateFile(MultipartFileDTO fileDto) throws Exception;

    /**
     * 사원의 프로필을 삭제합니다.
     *
     * @param empNo : 프로필을 삭제할 회원 번호
     */
    Long deleteProfile(Long empNo);


    // 사원 회사, 부서(조직)

    /**
     * 선택된 사원의 회사, 부서(조직) 정보를 조회합니다.
     *
     * @param empNo : 선택된 사원 번호
     */
    List<EmpOrgaSelectDTO> findOrgaById(Long empNo);

    /**
     * 사원의 회사, 부서(조직) 정보를 추가합니다.
     *
     * 한 조직이 추가 될 때,
     * 조직, 사원매핑 총 2개의 정보가 각각 추가됩니다.
     *
     * @param orgaReqDTO : 조직 정보
     * @param mapperReqDTO : 사원-조직 매핑 정보
     * @param empNo : 조직에 추가될 사원 번호
     */
    Long empOrgaInsert(EmpOrgaDTO orgaReqDTO, EmpMappingDTO mapperReqDTO, Long empNo);

    /**
     * 한 사원의 조직 정보를 업데이트합니다.
     *
     * 한 사원이 여러 부서에 속해있으므로
     * 여러 조직을 한 번에 수정할 수 있어 List로 받습니다.
     *
     * @param orgaUpdateListDTO : 사원의 조직 목록
     * @param modUser : 수정한 계정 이름
     */
    Long updateOrga(EmpOrgaUpdateListDTO orgaUpdateListDTO, String modUser);


    // 접속 중인 사원

    /**
     * 로그인한 사원의 정보를 조회합니다.
     *
     * 한 사원은 여러 회사에 소속될 수 있고,
     * 한 회사는 여러 부서를 가지고있는 계층형 구조이므로
     * List로 반환합니다.
     *
     * @param username : 로그인한 아이디
     */
    List<EmpLoginEmpDTO> findLoginUser(String username);

    /**
     * 현재 로그인하여 Redis에 올라간 사원의 정보를 조회합니다.
     *
     * @param username : 접속한 사원 아이디
     */
    EmpRedisDTO findLoggingInformation(String username);

    /**
     * 최근 로그인 ip와 시간을 업데이트합니다.
     *
     * @param username : 접속한 사원 아이디
     */
    Long updateRecentAccessInfo(String username);
}
