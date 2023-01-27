package com.dq.aquaranth.dept.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeptSearchParamDTO {

    /**
     * 부서 검색시 넘길 파라미터
     * 회사 번호, 검색내용(부서이름, 부서번호)
     */
//    private String deptName;
//    private Long deptNo, companyNo;
    private String deptSearch;
    private Long companyNo;

}
