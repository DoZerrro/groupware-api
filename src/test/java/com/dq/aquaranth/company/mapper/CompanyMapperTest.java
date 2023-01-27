package com.dq.aquaranth.company.mapper;

import com.dq.aquaranth.company.dto.*;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Log4j2
class CompanyMapperTest {

    @Autowired(required = false)
    CompanyMapper companyMapper;

    @Test
    @DisplayName("회사 일부정보가 포함된 전체 리스트 출력 Test 코드")
    void findAllCompany() {
        List<CompanyListDTO> companyListDTO = companyMapper.findAllCompany();
        companyListDTO.forEach(log::info);
        /**
         * companyListDTO 가 null 이 아닐 때 테스트 실행
         */
        assertThat(companyListDTO).isNotNull();
    }

    @Test
    @DisplayName("해당 회사에 대한 기본정보 출력 Test 코드")
    void findByCompanyNo() {
        long companyNo = 1L;
        CompanyInformationDTO companyInformationDTO
                = companyMapper.findByCompanyNo(companyNo);
        log.info(companyInformationDTO);
        assertThat(companyInformationDTO).isNotNull();
    }

    @Test
    @DisplayName("회사 기본정보 추가")
    void insertOrgaAndInsertCompany() {
        LocalDate foundingDate = LocalDate.of(2018, 7, 3);
        String username = "emp10";
        CompanyOrgaDTO companyOrgaDTO
                = CompanyOrgaDTO
                .builder()
                //.upperOrgaNo(null) => default 값을 null 이 들어가기 때문에 값을 굳이 주지 않아도 된다.
                .orgaType("company")
                .regUser(username)
                .build();
        Long orgaResult = companyMapper.insertOrga(companyOrgaDTO);

        CompanyInformationDTO companyInformationDTO
                = CompanyInformationDTO
                .builder()
                .orgaNo(companyOrgaDTO.getOrgaNo())
                .companyName("MEGAZONE CLOUD")
                .companyAddress("서울특별시 강남구 논현로85길 46 메가존빌딩")
                .companyTel("1644-2243")
                .foundingDate(foundingDate)
                .businessNumber("232-88-02486")
                .ownerName("이주완")
                .companyUse(true)
                .regUser(username)
                .build();
        Long companyResult = companyMapper.insert(companyInformationDTO);
        log.info("orgaInsertResult : " + orgaResult);
        log.info("companyInsertResult : " + companyResult);
    }

    @Test
    @DisplayName("회사 기본정보 수정 Test 코드")
    void update() {
        Long modCompanyNo = 13L;
        LocalDate modFoundingDate = LocalDate.of(1996,06,02);
        String username = "emp11";

        /**
         * 수정할 회사 번호가 존재할 때 테스트 실행
         */
        assertNotNull(companyMapper.findByCompanyNo(modCompanyNo));

        CompanyUpdateDTO companyUpdateDTO
                = CompanyUpdateDTO
                .builder()
                .companyNo(modCompanyNo)
                .companyName("NAVER")
                .companyAddress("경기도 성남시 분당구 정자일로 95")
                .companyTel("1588-3830")
                .ownerName("최수연")
                .foundingDate(modFoundingDate)
                .businessNumber("220-81-67539")
                .companyUse(true)
                .modUser(username)
                .build();

        Long modResult = companyMapper.update(companyUpdateDTO);
        log.info("modResult : " + modResult);
    }

    @Test
    @DisplayName("회사 사용 여부를 '미사용'으로 변경하여 삭제 Test 코드")
    void deleteByCompanyNo() {
        Long delCompanyNo = 12L;

        /**
         * 사용여부를 바꿀 즉, 삭제할 회사 번호가 존재할 때 테스트 실행
         */
        assertNotNull(companyMapper.findByCompanyNo(delCompanyNo));

        Long delResult = companyMapper.deleteByCompanyNo(delCompanyNo);
        log.info("delResult : " + delResult);
    }

    @Test
    @DisplayName("회사코드, 회사명, 사용여부로 회사 기본정보 검색 Test 코드")
    void search() {
        Long searchCompanyNo = 13L;         //검색할 회사코드
        String searchCompanyName = "N";     //검색할 회사명
        String searchCompanyUse = "true";    //검색할 사용여부

        CompanySearchDTO companySearchDTO = CompanySearchDTO
                .builder()
                .companyUse(searchCompanyUse)
                .companySearch(searchCompanyName)
                .build();

        List<CompanyListDTO> companyListDTO
                = companyMapper.search(companySearchDTO);
        companyListDTO.forEach(log::info);
    }
}
