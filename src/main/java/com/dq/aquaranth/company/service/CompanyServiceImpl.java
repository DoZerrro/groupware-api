package com.dq.aquaranth.company.service;

import com.dq.aquaranth.company.dto.*;
import com.dq.aquaranth.company.mapper.CompanyMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class CompanyServiceImpl implements CompanyService {

    private final CompanyMapper companyMapper;

    @Override
    public List<CompanyListDTO> findAllCompany() {
        log.info("회사 일부정보(회사번호, 회사명, 대표자명, 사용여부만)가 포함된 전체 리스트 출력");
        return companyMapper.findAllCompany();
    }

    @Override
    public CompanyInformationDTO findByCompanyNo(Long companyNo) {
        log.info("해당 회사에 대한 기본정보 출력");
        return companyMapper.findByCompanyNo(companyNo);
    }

    @Override
    @Transactional
    public Long insert(CompanyInformationDTO companyInformationDTO, String username) {

        Long resultInsert;

        //1. company
        //1-1. 조직 추가
        log.info("상위 조직번호가 없고 'company' 타입인 조직을 먼저 추가");
        CompanyOrgaDTO companyOrgaDTO = CompanyOrgaDTO
                .builder()
                .orgaType("company")
                .regUser(username)
                .build();
        companyMapper.insertOrga(companyOrgaDTO);

        //1-2. 회사 추가
        log.info("회사 기본정보 추가");
        companyInformationDTO.setOrgaNo(companyOrgaDTO.getOrgaNo());
        companyInformationDTO.setRegUser(username);
        resultInsert = companyMapper.insert(companyInformationDTO);


        //2. dept
        //2-1. 조직 추가
        log.info("상위 조직번호가 회사 조직번호고 'dept' 타입인 조직을 먼저 추가");
        log.info("상위 조직번호 : " + companyOrgaDTO.getOrgaNo());
        CompanyOrgaDTO companyOrgaDeptDTO = CompanyOrgaDTO
                .builder()
                .upperOrgaNo(companyOrgaDTO.getOrgaNo())
                .orgaType("dept")
                .regUser(username)
                .build();
        companyMapper.insertOrga(companyOrgaDeptDTO);

        //2-2. 부서 추가
        log.info("회사 추가 시 '대표이사' 부서 추가");
        CompanyDeptDTO companyDeptDTO = CompanyDeptDTO
                .builder()
                .companyNo(companyInformationDTO.getCompanyNo())
                .depth(1L)
                .deptName("대표이사")
                .deptDesc("대표이사")
                .regUser(username)
                .build();
        log.info("회사 번호 : " + companyInformationDTO.getCompanyNo());
        companyMapper.insertDept(companyDeptDTO);

        //2-3. 부서 매핑 추가
        log.info("회사 추가 시 부서 매핑 추가");
        CompanyDeptMappingDTO companyDeptMappingDTO = CompanyDeptMappingDTO
                .builder()
                .deptNo(companyDeptDTO.getDeptNo())
                .orgaNo(companyOrgaDeptDTO.getOrgaNo())
                .regUser(username)
                .build();
        log.info("부서 번호 : " + companyDeptMappingDTO.getDeptNo());
        log.info("조직 번호 : " + companyDeptMappingDTO.getOrgaNo());
        companyMapper.insertDeptMapping(companyDeptMappingDTO);


        return resultInsert;
    }

    @Override
    public Long update(CompanyUpdateDTO companyUpdateDTO, String username) {
        log.info("회사 기본정보 수정");
        companyUpdateDTO.setModUser(username);
        return companyMapper.update(companyUpdateDTO);
    }

    @Override
    public Long deleteByCompanyNo(Long companyNo) {
        log.info("회사 기본정보 삭제(즉, 사용 여부가 '사용'인 회사를 '미사용'으로 변경)");
        return companyMapper.deleteByCompanyNo(companyNo);
    }

    @Override
    public List<CompanyListDTO> search(String companyUse, String companySearch) {
        log.info("회사코드, 회사명, 사용여부로 회사 기본정보 검색");

        // 회사 사용여부와 검색정보를 DTO 에 넣어 넘긴다.
        CompanySearchDTO companySearchDTO = new CompanySearchDTO();
        companySearchDTO.setCompanyUse(companyUse);
        companySearchDTO.setCompanySearch(companySearch);
        return companyMapper.search(companySearchDTO);
    }

    @Override
    public List<CompanyListDTO> sort(String sortValue) {
        log.info("회사코드, 회사명, 대표자명, 사용여부로 정렬 후 회사 일부정보 리스트 출력");
        return companyMapper.sort(sortValue);
    }
}
