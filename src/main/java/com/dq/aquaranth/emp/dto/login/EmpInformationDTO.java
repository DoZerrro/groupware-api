package com.dq.aquaranth.emp.dto.login;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

/**
 * 접속한 사원의 정보와
 * 현재 로그인한 회사, 부서에 대한 정보 DTO 입니다.
 */
@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class EmpInformationDTO {
    List<EmpLoginEmpDTO> empList;
    EmpRedisDTO empLoginInfo;
}
