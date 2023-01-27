package com.dq.aquaranth.company.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompanySearchDTO {

    /**
     * 회사 검색 시 필요한 정보를 담을 DTO
     * 회사 사용여부, 검색내용(회사코드 및 이름)
     */

    private String companyUse, companySearch;
}
