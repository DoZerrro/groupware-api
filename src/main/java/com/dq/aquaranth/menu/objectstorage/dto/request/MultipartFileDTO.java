package com.dq.aquaranth.menu.objectstorage.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

/**
 * Multipart 파일 DTO입니다.
 * 클라이언트에서 전송받은 내용을 담고 있습니다.
 *
 * @author 김민준
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MultipartFileDTO {
    private String key;
    private MultipartFile multipartFile;
}
