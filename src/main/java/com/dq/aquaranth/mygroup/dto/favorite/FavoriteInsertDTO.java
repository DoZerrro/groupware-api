package com.dq.aquaranth.mygroup.dto.favorite;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FavoriteInsertDTO {

    /**
     * 해당 마이그룹에 사원을 즐겨찾기 할 때 필요한 DTO
     * 마이그룹 번호, 사원번호, 로그인한 사원의 아이디
     */
    private Long mygroupNo, orgaNo;
    private String regUser;
}
