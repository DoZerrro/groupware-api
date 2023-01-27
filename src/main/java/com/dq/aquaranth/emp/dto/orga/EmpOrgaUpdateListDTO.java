package com.dq.aquaranth.emp.dto.orga;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 한 사원은 여러 부서를 가질 수 있습니다.
 * 그리고 여러 부서의 정보는 한 번에 수정될 수 있기때문에
 * 수정된 정보들을 List로 받는 DTO 입니다.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmpOrgaUpdateListDTO {
    List<EmpOrgaUpdateDTO> list;
}
