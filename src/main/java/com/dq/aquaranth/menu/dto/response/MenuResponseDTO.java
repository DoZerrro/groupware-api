package com.dq.aquaranth.menu.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 메뉴 응답용 DTO입니다.
 *
 * @author 김민준
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MenuResponseDTO {
    private Long menuNo;
    private Long upperMenuNo;
    private String menuName;
    private boolean mainFlag;
    private String menuCode;
    private boolean deleteFlag;
    private String menuPath;
    private boolean menuRequired;
    private boolean menuAdmin;
    private Long menuOrder;
    private String menuDefault;
    private String uuid;
    private String filename;
    private Long depth;
    private String iconUrl;
}
