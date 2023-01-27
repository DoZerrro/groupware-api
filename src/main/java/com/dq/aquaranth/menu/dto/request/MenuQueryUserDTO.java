package com.dq.aquaranth.menu.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 일반 메뉴 서비스에서 사용되는
 * 유저 정보 조회용 DTO입니다.
 * @author 김민준
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MenuQueryUserDTO {
    private Long companyNo;
    private Long deptNo;
    private String username;
}
