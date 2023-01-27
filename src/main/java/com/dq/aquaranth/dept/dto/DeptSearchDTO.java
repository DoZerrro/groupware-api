package com.dq.aquaranth.dept.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeptSearchDTO {

    /**
     * 부서 검색 결과를 담을 DTO
     * 부서번호, 부서명, 소속경로
     */
    private String deptName, path;
    private Long deptNo;
    private boolean mainFlag;
}
