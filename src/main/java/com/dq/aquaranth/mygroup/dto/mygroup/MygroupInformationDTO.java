package com.dq.aquaranth.mygroup.dto.mygroup;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MygroupInformationDTO {

    /**
     * 해당 마이그룹을 조회한 정보를 담을 DTO
     * 마이그룹 번호, 이름
     */
    private Long mygroupNo;
    private String mygroupName;
}
