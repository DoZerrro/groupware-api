package com.dq.aquaranth.mygroup.dto.orgatree;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrgaTreeEmpListDTO {

    /**
     * 해당 부서에 속한 사원을 모두 조회 후 정보를 담을 DTO
     * 사원의 조직 번호, 이름, 직책, 아이디, 소속부서, 전화번호
     */
    private Long orgaNo;
    private String empName, empRank, username, path, empPhone;
}
