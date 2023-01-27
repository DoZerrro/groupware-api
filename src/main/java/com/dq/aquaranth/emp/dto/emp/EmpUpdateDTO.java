package com.dq.aquaranth.emp.dto.emp;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 사원 정보를 수정했을 때,
 * 수정한 정보로 값을 업데이트 하기 위한 DTO 입니다.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmpUpdateDTO {
    private Long empNo;
    @NotBlank
    private String empName, gender;
    private String empPhone;
    private String empAddress;
    private String email;
    private String modUser;
    private String lastLoginIp;
    private boolean empUse;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate lastRetiredDate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime  modDate;
}
