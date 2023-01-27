package com.dq.aquaranth.company.mapper;

import com.dq.aquaranth.company.dto.*;

import java.util.List;

public interface CompanyMapper {

    /**
     * 회사 일부정보(회사번호, 회사명, 대표자명, 사용여부만)가 포함된 전체 리스트 출력
     */
    List<CompanyListDTO> findAllCompany();

    /**
     * 해당 회사에 대한 기본정보 출력
     */
    CompanyInformationDTO findByCompanyNo(Long companyNo);

    /**
     * 상위 조직번호가 없고 'company' 타입인 조직 추가
     */
    Long insertOrga(CompanyOrgaDTO companyOrgaDTO);

    /**
     * 회사 기본정보 추가
     */
    Long insert(CompanyInformationDTO companyInformationDTO);

    /**
     * 회사 추가 시 부서 추가
     */
    Long insertDept(CompanyDeptDTO companyDeptDTO);

    /**
     * 회사 추가 시 부서 매핑 추가
     */
    Long insertDeptMapping(CompanyDeptMappingDTO companyDeptMappingDTO);

    /**
     * 회사 기본정보 수정
     */
    Long update(CompanyUpdateDTO companyUpdateDTO);

    /**
     * 회사 기본정보 삭제(즉, 사용 여부가 '사용'인 회사를 '미사용'으로 변경)
     */
    Long deleteByCompanyNo(Long companyNo);

    /**
     * 회사코드, 회사명, 사용여부로 회사 기본정보 검색
     */
    List<CompanyListDTO> search(CompanySearchDTO companySearchDTO);

    /**
     * 회사코드, 회사명, 대표자명, 사용여부로 정렬 후 회사 일부정보 리스트 출력
     */
    List<CompanyListDTO> sort(String sortValue);

    /**
     * 사용자 아이디에 맞는 회사 정보 출력
     */
    CompanyInformationDTO findByUsername(String username);

}
