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
public class CompanyUpdateDTO {

    /**
     * 회사 정보 수정 시 정보를 담을 DTO
     * 회사번호(수정할 회사 선택 시 가져옴)
     * 회사이름, 회사주소, 회사전화번호, 대표자명, 사업자번호, 등록자
     * 설립일, 사용여부
     */
    private Long companyNo;
    private String companyName, companyAddress, companyTel, ownerName, businessNumber, modUser;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate foundingDate;
    private Boolean companyUse;
}
