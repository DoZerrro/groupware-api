package com.dq.aquaranth.dept.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeptMemberDTO {

    /**
     * 부서명, 직급, 사용자명 , 아이디, 전화번호가 필요
     * orgaNO => 사원 조직번호
     */
    private String deptName, empRank, empName, username, empPhone;
    private Long orgaNo;

}
