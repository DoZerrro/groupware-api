package com.dq.aquaranth.menu.objectstorage.dto.request;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

/**
 * ObjectStorage에 추가할 Multipart-File과 파일명이 담긴 클래스 입니다.
 *
 * @author 김민준
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ObjectPostRequestDTO {
    private String filename;
    private MultipartFile multipartFile;
}
