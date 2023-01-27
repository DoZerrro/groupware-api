package com.dq.aquaranth.rolegroup.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MenuRole {
    private Long roleGroupNo;
    private Long menuNo;
    private String regUser;
    private LocalDateTime regDate;
    private String modUser;
    private LocalDateTime modDate;
}
