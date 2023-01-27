package com.dq.aquaranth.userrole.dto.tree;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TreeReqOrgaListDTO {
    Long orgaNo;
    String option, keyword;
    boolean recursive;
}
