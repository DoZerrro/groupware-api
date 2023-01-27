package com.dq.aquaranth.login.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginUser {
    private String username;
    private Long loginCompanyNo;
    private Long loginDeptNo;
}
