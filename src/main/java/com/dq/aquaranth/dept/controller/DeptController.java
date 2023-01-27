package com.dq.aquaranth.dept.controller;

import com.dq.aquaranth.dept.dto.*;
import com.dq.aquaranth.dept.service.DeptService;
import com.dq.aquaranth.menu.annotation.MenuCode;
import com.dq.aquaranth.menu.constant.MenuCodes;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@Log4j2
@RequestMapping("/api/dept2")
@RequiredArgsConstructor
@MenuCode(MenuCodes.ORGA0020)
public class DeptController {

    private final DeptService deptService;

    /**
     * 부서 1개 상세 조회
     * @param dept_no
     * @return
     */
    @GetMapping("/{dept_no}")
    public DeptDTO getOne(@PathVariable("dept_no") Long dept_no){

        DeptDTO result = deptService.getOne(dept_no);

        return result;

    }

    /**
     * 부서 등록
     * @param deptRegisterDTO
     * @param authentication
     * @return
     */
    @PostMapping
    public Integer register(@RequestBody DeptRegisterDTO deptRegisterDTO, Authentication authentication) {

        log.info(deptRegisterDTO);

        //로그인한 사용자의 username 가져오기
        String username = authentication.getName();
        log.info("username : " + username);
        deptRegisterDTO.setUsername(username);

//      Map<Object, Object> result = deptService.insert(deptDTO2);
        Integer result = deptService.insert(deptRegisterDTO);

        return result;
    }

    /**
     * 삭제 (부서 사용여부가 '사용'인 부서를 '미사용' 으로 변경)
     */
    @GetMapping ("/remove/{deptNo}")
    public Long removeDept(@PathVariable Long deptNo) {
        return deptService.delete(deptNo);
    }

    /**
     * 부서 정보 수정
     * @param dept_no
     * @param deptDTO
     * @return
     */
    @PutMapping("/{dept_no}")
    public DeptDTO modify(@PathVariable("dept_no") Long dept_no , @RequestBody DeptDTO deptDTO) {
        log.info("controller modify : " + deptDTO);
        DeptDTO modifyDTO = deptService.modify(deptDTO);

        log.info("modifyDTO : " + modifyDTO);

        return modifyDTO;

    }

    /**
     * 부서 회사 번호별로 조회
     * @param companyNo
     * @return
     */
    @GetMapping("/list/{companyNo}")
    public List<DeptDTO> listDept (@PathVariable Long companyNo) {

        return deptService.listDept(companyNo);
    }


    /**
     * 상위 부서 바로 밑 하위부서 조회
     * @param upper_dept_no
     * @param depth
     * @return
     */
    @GetMapping("/list/{upper_dept_no}/{depth}")
    public List<DeptDTO> listDept2 (@PathVariable("upper_dept_no") Long upper_dept_no, @PathVariable("depth") int depth) {

        return deptService.listDepth(upper_dept_no, depth);
    }

    /**
     * 트리 구조 조회
     * @param companyCode
     * @return
     */
    @GetMapping("/tree/{companyCode}")
    public List<DeptTreeDTO> listTree(@PathVariable("companyCode") Long companyCode ){

        return deptService.getTree(companyCode);
    }

    /**
     * 회사 바로 출력 하고 클릭하면 바로 밑 하위 부서 조회
     * @param getSubDeptDTO
     * @return
     */
    @GetMapping("/findTree/{companyNo}/{depth}/{upperDeptNo}")
    public List<DeptDTO> findTree(GetSubDeptDTO getSubDeptDTO) {
        return deptService.getSubDepth(getSubDeptDTO);
    }

    /**
     * 부서 검색
     * @return
     */
    @GetMapping("/search")
    public List<DeptSearchDTO> searchDept(DeptSearchParamDTO deptSearchParamDTO) {
        return deptService.searchList(deptSearchParamDTO);
    }

    /**
     * 부서에 해당하는 부서원 조회
     * @param orgaNo
     * @return
     */
    @GetMapping("/member/{orgaNo}")
    public List<DeptMemberDTO> findByDeptOrgaNo(@PathVariable("orgaNo") Long orgaNo){
        return deptService.deptMember(orgaNo);
    }


    /**
     * 해당 회사의 부서 목록 출력
     * @param companyNo
     * @return
     */
    @GetMapping("/readName/{companyNo}")
    public List<DepartmentDTO> getDeptName(@PathVariable("companyNo") Long companyNo) {
        return deptService.findByCompanyNo(companyNo);
    }

}
