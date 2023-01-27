package com.dq.aquaranth.company.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CompanyOrgaDTO {

    /**
     * 회사 등록 시 조직 테이블에 추가할 때 필요한 정보를 담을 DTO
     * 조직번호(auto increment), 상위 조직번호, 조직타입, 등록자
     */
    private Long orgaNo, upperOrgaNo;
    private String orgaType, regUser;
}
