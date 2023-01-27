package com.dq.aquaranth.mygroup.dto.orgatree;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrgatreeEmpInformationDTO {

    /**
     * 해당 부서에 소속된 사원 중 한 사원을 조회 후 정보를 담을 DTO
     * 사원의 번호, 조직 번호, 이름, 직책, 아이디, 소속부서, 전화번호, 이메일, 프로필
     */
    private Long empNo, orgaNo;
    private String empName, empRank, username, path, empPhone, email;
    private String filename, uuid, profileUrl;
}
