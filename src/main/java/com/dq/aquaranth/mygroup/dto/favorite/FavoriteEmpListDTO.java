package com.dq.aquaranth.mygroup.dto.favorite;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FavoriteEmpListDTO {

    /**
     * 해당 마이그룹에 즐겨찾기 된 모든 사원 정보를 담을 DTO
     * 마이그룹 번호, 조직 번호, 사원 이름, 직급, 아이디, 소속부서, 전화번호
     */
    private Long orgaNo, mygroupNo, empNo;
    private String empName, empRank, username, path, empPhone;
}
