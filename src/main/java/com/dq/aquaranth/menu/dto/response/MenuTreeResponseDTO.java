
package com.dq.aquaranth.menu.dto.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 메뉴 트리용 DTO입니다.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MenuTreeResponseDTO {
    private Long menuNo;
    @Builder.Default
    private Long upperMenuNo = 0L;
    private Long depth;
    private String menuName;
    private String menuCode;
    private boolean mainFlag;
    private String menuPath;
    private String uuid;
    private String filename;
    private String iconUrl;
    private Long subMenuCount;
}
