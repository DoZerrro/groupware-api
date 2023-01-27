package com.dq.aquaranth.company.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CompanyInformationDTO {

    /**
     * 회사 등록 시 필요한 정보를 담을 DTO
     * 회사번호, 조직번호(last insert id)
     * 회사이름, 회사주소, 회사전화번호, 대표자명, 사업자번호, 등록자
     * 설립일, 사용여부
     */
    private Long companyNo, orgaNo;
    private String companyName, companyAddress, companyTel, ownerName, businessNumber, regUser;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate foundingDate;
    private Boolean companyUse;
}
