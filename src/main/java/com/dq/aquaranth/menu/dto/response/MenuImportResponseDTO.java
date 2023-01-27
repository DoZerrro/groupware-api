package com.dq.aquaranth.menu.dto.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 클라이언트 앱 초기화용 DTO입니다.
 *
 * @author 김민준
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MenuImportResponseDTO {
    private String menuPath;
    private Long menuNo;
    private String menuName;
}
