package com.dq.aquaranth.dept.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DeptListDTO {

    // 부서 검색을 통해 나오는 컬럼들
    private Long deptNo;
    private String deptName;
}
