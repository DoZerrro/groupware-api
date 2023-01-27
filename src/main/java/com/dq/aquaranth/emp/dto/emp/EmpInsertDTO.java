package com.dq.aquaranth.emp.dto.emp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 사원을 추가할 때, 입력되어 받아온 모든 정보를 위한 DTO 입니다.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmpInsertDTO {
    private String regUser;

    //사원 정보
    private Long empNo;
    @NotBlank
    private String empName, username, password;
    private String gender;
    private String empPhone;
    private String empAddress;
    private String email;
    private LocalDateTime lastLoginTime;
    private String lastLoginIp;
    private LocalDate firstHiredDate;
    private LocalDate lastRetiredDate;

    //부서 정보
    private Long deptNo;

    //조직 정보
    private Long orgaNo;
    private String orgaType;

    //사원 조직 매핑
    private String empRank;
    private String empRole;
    private LocalDate hiredate;

}
