package com.dq.aquaranth.company.service;

import com.dq.aquaranth.company.dto.CompanyInformationDTO;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Log4j2
class CompanyServiceImplTest {

    @Autowired
    CompanyService companyService;

    @Test
    @DisplayName("회사 추가 시 부서 및 부서 매핑 추가")
    void insert() {
        Long resultCompany;

        String username = "dozzze";
        LocalDate foundingDate = LocalDate.of(2013,7,1);

        CompanyInformationDTO companyInformationDTO = CompanyInformationDTO
                .builder()
                .companyName("COUPANG")
                .companyAddress("서울특별시 송파구 송파대로 570")
                .companyTel("1577-7011")
                .ownerName("강한승")
                .businessNumber("120-88-03579")
                .foundingDate(foundingDate)
                .companyUse(true)
                .build();

        resultCompany = companyService.insert(companyInformationDTO, username);
        log.info("회사 추가 결과 : " + resultCompany);
    }
}
