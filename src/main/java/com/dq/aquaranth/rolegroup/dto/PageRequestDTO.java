package com.dq.aquaranth.rolegroup.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Log4j2
public class PageRequestDTO {

    @Builder.Default
    private int page = 1;
    @Builder.Default
    private int size = 10;

    // keyword
    private Long companyNo;
    private String roleGroupName;

    // size 최저값은 10, 최대값은 30
    public int getSkip(){
        size = size <= 10? 10: size >= 30? 30:size;
        return (page - 1) * size;
    }

}
