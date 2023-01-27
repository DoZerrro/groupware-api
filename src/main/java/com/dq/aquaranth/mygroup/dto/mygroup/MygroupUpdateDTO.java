package com.dq.aquaranth.mygroup.dto.mygroup;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MygroupUpdateDTO {

    /**
     * 로그인한 사원의 마이그룹 수정할 때 필요한 정보를 담을 DTO
     * 수정할 마이그룹 이름, 번호, 로그인한 사원의 아이디
     */
    String mygroupName, modUser;
    Long mygroupNo;
}
