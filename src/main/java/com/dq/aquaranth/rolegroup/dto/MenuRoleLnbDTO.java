package com.dq.aquaranth.rolegroup.dto;

import lombok.Data;

@Data
public class MenuRoleLnbDTO {
    private Long menuNo;
    private String menuName;
    private int depth;
    private int upperMenuNo;
    private String menuPath;
    private boolean checked;
}
