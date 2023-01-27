package com.dq.aquaranth.menu.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

/**
 * 메뉴 추가용 DTO입니다.
 *
 * @author 김민준
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MenuInsertDTO {
    private Long upperMenuNo;
    private Long menuNo;
    private String menuCode;
    private String menuName;
    private boolean mainFlag;
    private Long menuOrder;
    private MultipartFile multipartFile;

    private String menuPath;
    private Long depth;
    private String uuid;
    private String filename;
    private String regUser;

}
