package com.dq.aquaranth.mygroup.dto.mygroup;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MygroupListDTO {

    /**
     * 로그인한 사원의 마이그룹 리스트를 담을 DTO
     * 마이그룹 번호, 이름, 로그인한 사원의 사원번호, 마이그룹에 즐겨찾기된 총 사원 수
     */
    private Long mygroupNo, empNo;
    private Integer countEmp;
    private String mygroupName;
}
