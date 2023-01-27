package com.dq.aquaranth.menu.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 메뉴 상세조회용 DTO입니다.
 *
 * @author 김민준
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MenuDetailResponseDTO {
    private Long upperMenuNo;
    private String upperMenuName;
    private String menuName;
    private String menuCode;
    private boolean mainFlag;
    private Long menuOrder;
    private String uuid;
    private String filename;
    private String iconUrl;
}
