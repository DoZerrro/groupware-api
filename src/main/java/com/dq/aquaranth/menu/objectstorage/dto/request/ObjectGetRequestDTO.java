package com.dq.aquaranth.menu.objectstorage.dto.request;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ObjectStorage에서 요청할 파일명이 담긴 클래스 입니다.
 * URL의 만료 시간과 함께 담을 수 있습니다.
 *
 * @author 김민준
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ObjectGetRequestDTO {
    private String filename;
    private Integer time;
}
