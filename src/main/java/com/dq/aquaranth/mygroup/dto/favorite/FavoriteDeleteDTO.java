package com.dq.aquaranth.mygroup.dto.favorite;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FavoriteDeleteDTO {

    /**
     * 해당 마이그룹에 즐겨찾기 된 사원을 삭제할 때 필요한 DTO
     * 마이그룹 번호, 사원의 조직번호
     */
    private Long mygroupNo, orgaNo;
}
