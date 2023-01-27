package com.dq.aquaranth.menu.objectstorage.dto.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ObjectStorage에서 요청한 URL이 담긴 클래스 입니다.
 *
 * @author 김민준
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ObjectResponseDTO {
    private String url;
}
