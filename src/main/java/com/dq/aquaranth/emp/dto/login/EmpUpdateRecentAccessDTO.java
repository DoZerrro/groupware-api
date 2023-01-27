package com.dq.aquaranth.emp.dto.login;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 사원이 로그인 했을 때,
 * 최근 로그인 시간과 최근 로그인 IP를 업데이트하기 위한 DTO 입니다.
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmpUpdateRecentAccessDTO {
    private Long empNo;
    private String lastLoginIp;
    private LocalDateTime lastLoginTime;
}
