package com.dq.aquaranth.emp.dto.login;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;


/**
 * 현재 로그인한 사원의 정보 DTO 입니다.
 * (접속 변경된 회사, 부서 선택에 따라 변하지 않는 정보)
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class EmpLoginEmpDTO {
    private Long empNo;
    private String empName;
    private String lastLoginIp;
    private String username;
    private String loginIp;
    private String profileUrl;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime lastLoginTime;

    private List<EmpLoginCompanyDTO> companyList;
}
