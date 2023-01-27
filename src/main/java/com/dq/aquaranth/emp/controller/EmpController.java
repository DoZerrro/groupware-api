package com.dq.aquaranth.emp.controller;

import com.dq.aquaranth.emp.dto.emp.*;
import com.dq.aquaranth.emp.dto.login.EmpLoginEmpDTO;
import com.dq.aquaranth.emp.dto.orga.EmpOrgaDTO;
import com.dq.aquaranth.emp.dto.orga.EmpOrgaInsertDTO;
import com.dq.aquaranth.emp.dto.orga.EmpOrgaUpdateListDTO;
import com.dq.aquaranth.emp.dto.orga.EmpOrgaSelectDTO;
import com.dq.aquaranth.emp.service.EmpService;
import com.dq.aquaranth.login.domain.LoginUser;
import com.dq.aquaranth.login.service.UserSessionService;
import com.dq.aquaranth.menu.annotation.MenuCode;
import com.dq.aquaranth.menu.constant.MenuCodes;
import com.dq.aquaranth.menu.objectstorage.dto.request.MultipartFileDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@RestController
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/api/emp")
@MenuCode(MenuCodes.ORGA0030)
public class EmpController {
    private final EmpService empService;
    private final UserSessionService userSessionService;

    /**
     * 모든 사원의 정보를 조회합니다.
     */
    @GetMapping("/information")
    public List<EmpDTO> getEmpList() {
        return empService.findAll();
    }

    /**
     * 선택된 사원의 모든 정보를 조회합니다.
     */
    @GetMapping("/read/{empNo}")
    public EmpDTO getEmp(@PathVariable("empNo") Long empNo) {
        return empService.findById(empNo);
    }

    /**
     * 사원을 추가합니다.
     */
    @PostMapping("/register")
    public EmpDTO registerEmp (@Valid @RequestBody EmpInsertDTO reqDTO, Authentication authentication) throws IllegalAccessException{
        log.info(reqDTO);

        String registrant = authentication.getName();

        //조직
        EmpOrgaDTO orgaDTO = EmpOrgaDTO.builder()
                .deptNo(reqDTO.getDeptNo())
                .regUser(registrant)
                .build();

        //조직 테이블의 last_insert_id 저장
        Long orgaId = orgaDTO.getOrgaNo();

        //사원
        EmpDTO empDTO = EmpDTO.builder()
                .empName(reqDTO.getEmpName())
                .username(reqDTO.getUsername())
                .password(reqDTO.getPassword())
                .gender(reqDTO.getGender())
                .empPhone(reqDTO.getEmpPhone())
                .empAddress(reqDTO.getEmpAddress())
                .email(reqDTO.getEmail())
                .firstHiredDate(LocalDate.now())
                .regUser(registrant)
                .build();

        //사원 테이블의 last_insert_id 저장
        Long empNo = empDTO.getEmpNo();

        //사원 매핑
        EmpMappingDTO empMappingDTO = EmpMappingDTO.builder()
                .empNo(empNo)
                .orgaNo(orgaId)
                .empRank(reqDTO.getEmpRank())
                .empRole(reqDTO.getEmpRole())
                .regUser(registrant)
                .build();

        empService.insert(orgaDTO, empDTO, empMappingDTO);

        return empDTO;
    }

    /**
     * 사원의 정보를 수정합니다.
     */
    @PutMapping(value = "/modify/{empNo}")
    public Long modifyEmp(@Valid @RequestBody EmpUpdateDTO empUpdateDTO, Authentication authentication) {
        String username = authentication.getName();
        empUpdateDTO.setModUser(username);

        return empService.update(empUpdateDTO);
    }

    /**
     * 사원의 프로필 사진을 업데이트합니다.
     */
    @PutMapping(value = "/updateprofile", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public Long updateEmpProfile(MultipartFileDTO fileDto) throws Exception {
        return empService.updateFile(fileDto);
    }

    /**
     * 사원의 프로필 사진을 삭제합니다.
     */
    @PutMapping(value = "/removeProfile/{empNo}")
    public long removeProfile(@PathVariable("empNo") Long empNo){
        return empService.deleteProfile(empNo);
    }

    /**
     * 선택된 사원의 모든 회사, 부서(조직) 정보를 조회합니다.
     */
    @GetMapping("/readOrga/{empNo}")
    public List<EmpOrgaSelectDTO> empOrgaList(@PathVariable("empNo") Long empNo) {
        return empService.findOrgaById(empNo);
    }

    /**
     * 사원의 회사, 부서(조직) 정보를 추가합니다.
     */
    @PostMapping("/registerOrga")
    public Long registerEmpOrga (@RequestBody EmpOrgaInsertDTO reqDTO, Authentication authentication){
        String registrant = authentication.getName();
        Long empNo = reqDTO.getEmpNo();

        //조직
        EmpOrgaDTO orgaDTO = EmpOrgaDTO.builder()
                .deptNo(reqDTO.getDeptNo())
                .regUser(registrant)
                .build();

        //조직 테이블의 last_insert_id 저장
        Long orgaNo = orgaDTO.getOrgaNo();

        //사원 매핑
        EmpMappingDTO empMappingDTO = EmpMappingDTO.builder()
                .empNo(empNo)
                .orgaNo(orgaNo)
                .empRank(reqDTO.getEmpRank())
                .regUser(registrant)
                .build();

        return empService.empOrgaInsert(orgaDTO, empMappingDTO, empNo);
    }

    /**
     * 사원의 조직 정보를 수정합니다.
     */
    @PutMapping(value = "/modifyOrga")
    public Long modifyOrga(@RequestBody EmpOrgaUpdateListDTO orgaUpdateListDTO, Authentication authentication) {
        String modUser = authentication.getName();

        return empService.updateOrga(orgaUpdateListDTO, modUser);
    }

    /**
     * 로그인한 사원의 회사, 부서, 사원 정보를 조회합니다.
     */
    @GetMapping("/loginlist")
    public List<EmpLoginEmpDTO> findLoginUser(Authentication authentication){
        String username = authentication.getName();
        return empService.findLoginUser(username);
    }

    /**
     * 로그인한 사원의 정보를 Redis에 등록합니다.
     */
    @PostMapping("/registerLoginUser")
    public LoginUser registerLoginUser(@RequestBody LoginUser loginUser, Authentication authentication) {
        log.error(loginUser);
        String username = authentication.getName();
        loginUser.setUsername(username);
        // 접속한 아이디에 대한 정보를 레디스에 올린다.
        userSessionService.loadUserInfoByLoginUser(loginUser);
        // 현재 ip와 시간을 업데이트한다.
        empService.updateRecentAccessInfo(username);

        return loginUser;
    }
}
