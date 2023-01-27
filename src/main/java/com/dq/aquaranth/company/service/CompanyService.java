package com.dq.aquaranth.company.service;

import com.dq.aquaranth.company.dto.*;

import java.util.List;

public interface CompanyService {

    List<CompanyListDTO> findAllCompany();
    CompanyInformationDTO findByCompanyNo(Long companyNo);
    Long insert(CompanyInformationDTO companyInformationDTO, String username);
    Long update(CompanyUpdateDTO companyUpdateDTO, String username);
    Long deleteByCompanyNo(Long companyNo);
    List<CompanyListDTO> search(String companyUse, String companySearch);
    List<CompanyListDTO> sort(String sortValue);
}

