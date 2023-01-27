package com.dq.aquaranth.menu.dto.request;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 메뉴 검색용 DTO입니다.
 *
 * @author 김민준
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MenuQueryDTO {
    private Long upperMenuNo;
    private Long menuNo;
    private String menuCode;
    private String keyword;
}
