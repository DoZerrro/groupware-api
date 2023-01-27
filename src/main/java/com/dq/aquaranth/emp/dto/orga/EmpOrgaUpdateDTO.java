package com.dq.aquaranth.emp.dto.orga;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * 사원의 부서를 수정할 때
 * 조직(Orga) 정보 수정을 위한 DTO 입니다.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmpOrgaUpdateDTO {
    private Long orgaNo;
    private Long deptNo;
    private Long companyNo;
    private Long empNo;
    private String empRank;
    private String empRole;
    private String companyAddress;
    private String companyName;
    private String companyTel;
    private String deptName;
    private String modUser;
    private boolean deptMain;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate retiredDate, hiredDate;


}
