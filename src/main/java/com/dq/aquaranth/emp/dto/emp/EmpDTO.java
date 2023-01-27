package com.dq.aquaranth.emp.dto.emp;

import lombok.*;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 사원 DTO 입니다.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmpDTO {
    private Long empNo;
    @NotBlank
    private String empName, username, password, gender;
    private String empPhone;
    private String empAddress;
    private String email;
    private String lastLoginIp;
    private String regUser;
    private String modUser;
    private String uuid;
    private String filename;
    private String profileUrl;
    private boolean empUse;
    private LocalDateTime lastLoginTime;
    private LocalDateTime regDate;
    private LocalDateTime modDate;
    private LocalDate firstHiredDate, lastRetiredDate;
}
