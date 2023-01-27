package com.dq.aquaranth.menu.dto.request;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 메뉴 아이콘 업데이트용 DTO입니다.
 * @author 김민준
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MenuIconUpdateDTO {
    private String menuCode;
    private String menuNo;
    private String uuid;
    private String filename;
    private String modUser;
}
