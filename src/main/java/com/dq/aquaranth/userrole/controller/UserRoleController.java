package com.dq.aquaranth.userrole.controller;

import com.dq.aquaranth.menu.annotation.MenuCode;
import com.dq.aquaranth.menu.constant.MenuCodes;
import com.dq.aquaranth.userrole.dto.paging.PageRequestDTO;
import com.dq.aquaranth.userrole.dto.paging.PageResponseDTO;
import com.dq.aquaranth.userrole.dto.request.UserRoleReqInsertOrgaRoleDTO;
import com.dq.aquaranth.userrole.dto.request.UserRoleReqRemoveOrgaRoleDTO;
import com.dq.aquaranth.userrole.dto.request.UserRoleReqRemoveUserRoleDTO;
import com.dq.aquaranth.userrole.dto.response.*;
import com.dq.aquaranth.userrole.service.UserRoleService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/api/userrole")
@MenuCode(MenuCodes.ROLE0020)
public class UserRoleController {

    private final UserRoleService userRoleService;

    /**
     * 모든 회사 정보를 조회합니다
     *
     * @return UserRoleCompanyDTO 의 List를 반환합니다
     * @author 박준성
     */
    @Operation(summary = "모든 회사 정보 조회", description = "사용 가능한 모든 회사 정보를 조회합니다.")
    @GetMapping("/companyListAll")
    public List<UserRoleCompanyDTO> findCompanyAll() {
        return userRoleService.findCompanyAll();
    }

    /**
     * 로그인한 사용자의 회사 정보를 조회합니다
     * @param authentication - 로그인한 사용자의 정보를 담은 객체
     * @return UserRoleCompanyDTO 의 List를 반환합니다
     * @author 박준성
     */
    @Operation(summary = "회사 정보 조회", description = "로그인한 사용자의 계정 정보를 사용하여 회사 정보를 조회합니다.")
    @GetMapping("/companyList")
    public List<UserRoleCompanyDTO> findCompany(Authentication authentication){
        return userRoleService.findCompany(authentication.getName());
    }

    /**
     * 선택된 회사의 권한그룹 목록을 조회합니다.
     * @param pageRequestDTO - 페이지 정보 및 검색정보(page, size, keyword)를 담고 있는 객체
     * @return UserRoleCompanyDTO 의 List를 반환합니다
     * @author 박준성
     */
    @Operation(summary = "권한 그룹 목록 조회", description = "선택한 회사에 부여 가능한 모든 권한그룹의 목록을 조회합니다.")
    @GetMapping("/roleGroupList")
    public PageResponseDTO<UserRoleRoleGroupBasedListDTO> findRoleGroupByCompanyNo(PageRequestDTO pageRequestDTO) {
        return userRoleService.findRoleGroupByOrgaNo(pageRequestDTO);
    }

    /**
     * 선택된 권한그룹이 부여된 조직(회사/부서/사원) 의 목록을 조회합니다.
     * @param pageRequestDTO - 페이지 정보 및 검색정보(page, size, keyword)를 담고 있는 객체
     * @return UserRoleCompanyDTO 의 List를 반환합니다
     * @author 박준성
     */
    @Operation(summary = "조직(회사/부서/사원) 목록 조회", description = "선택한 권한그룹이 부여된 조직(회사/부서/사원) 목록을 조회합니다.")
    @GetMapping("/roleGroupUserList")
    public PageResponseDTO<UserRoleGroupBasedUserListDTO> findOrgaByRoleGroupNo(PageRequestDTO pageRequestDTO) {
        return userRoleService.findOrgaByRoleGroupNo(pageRequestDTO);
    }

    /**
     * 권한그룹을 선택하고 조직(회사/부서/사원) 팝업에서 전달받은 정보를 통하여 권한그룹을 부여합니다.
     * @param userRoleReqInsertOrgaRoleDTO - 권한그룹번호, 권한그룹을 부여할 조직(회사/부서/사원)의 orga_no 목록 전달
     * @param authentication - 로그인한 사용자의 정보를 담은 객체
     * @return UserRoleResponseDTO - 결과의 상태(state), 제목(title), 메시지(message)를 담은 객체 반환
     * @author 박준성
     */
    @Operation(summary = "권한그룹 부여", description = "공통 조직도에서 선택한 조직(회사/부서/사원)에 선택한 권한그룹을 부여합니다.")
    @PostMapping("/insertOrgaRole")
    public UserRoleResponseDTO insertOrgaRole(@RequestBody UserRoleReqInsertOrgaRoleDTO userRoleReqInsertOrgaRoleDTO, Authentication authentication) {
        userRoleReqInsertOrgaRoleDTO.setUsername(authentication.getName());

        UserRoleResponseDTO result;
        try{
            userRoleService.insertUserRole(userRoleReqInsertOrgaRoleDTO);

            result = UserRoleResponseDTO
                    .builder()
                    .title("등록 완료")
                    .message("권한그룹이 부여되었습니다.")
                    .state("success")
                    .build();
        }catch(RuntimeException re){
            result = UserRoleResponseDTO
                    .builder()
                    .state("error")
                    .title("등록 실패")
                    .message(re.getMessage())
                    .build();
        }catch(Exception e){
            result = UserRoleResponseDTO
                    .builder()
                    .state("error")
                    .title("등록 실패")
                    .message("관리자에게 문의해주세요.")
                    .build();
        }
        return result;
    }

    /**
     * 선택된 조직(회사/부서/사원)의 권한그룹을 해제합니다.
     * @param userRoleReqRemoveOrgaRoleDTO - 권한그룹번호, 권한그룹을 해제할 조직(회사/부서/사원)의 orga_no 목록 전달
     * @return UserRoleResponseDTO - 결과의 상태(state), 제목(title), 메시지(message)를 담은 객체 반환
     * @author 박준성
     */
    @Operation(summary = "부여된 권한그룹 제거", description = "선택한 조직(회사/부서/사원)에 부여된 권한그룹을 제거합니다.")
    @PostMapping("/removeOrgaRole")
    public UserRoleResponseDTO removeOrgaRole(@RequestBody UserRoleReqRemoveOrgaRoleDTO userRoleReqRemoveOrgaRoleDTO) {
        UserRoleResponseDTO result;
        try{
            result = userRoleService.removeOrgaRole(userRoleReqRemoveOrgaRoleDTO);
        }catch(RuntimeException re){
            result = UserRoleResponseDTO
                    .builder()
                    .state("error")
                    .title("삭제 실패")
                    .message(re.getMessage())
                    .build();
        }catch(Exception e){
            result = UserRoleResponseDTO
                    .builder()
                    .state("error")
                    .title("삭제 실패")
                    .message("관리자에게 문의해주세요.")
                    .build();
        }
        return result;
    }

    /**
     * 선택한 회사의 사용자 목록을 조회합니다.
     * @param pageRequestDTO - 페이지 정보 및 검색정보(page, size, keyword)를 담고 있는 객체
     * @return UserRoleUserListBasedDTO 의 List를 반환합니다
     * @author 박준성
     */
    @Operation(summary = "사용자 목록 조회", description = "선택한 회사에 소속된 모든 사용자의 목록을 조회합니다.")
    @GetMapping("/findUserListByOrgaNo")
    public PageResponseDTO<UserRoleUserListBasedDTO> findUserListByOrgaNo(PageRequestDTO pageRequestDTO){
        return userRoleService.findUserListByOrgaNo(pageRequestDTO);
    }

    /**
     * 선택한 사용자에 부여된 모든 권한그룹을 조회합니다.
     * @param pageRequestDTO - 페이지 정보 및 검색정보(page, size, keyword)를 담고 있는 객체
     * @return UserRoleUserBasedRoleGroupDTO 의 List를 반환합니다
     * @author 박준성
     */
    @Operation(summary = "권한그룹 목록 조회", description = "선택한 사용자에 부여된 모든 권한그룹을 조회합니다.")
    @GetMapping("/findRoleGroupByUser")
    public PageResponseDTO<UserRoleUserBasedRoleGroupDTO> findRoleGroupByUser(PageRequestDTO pageRequestDTO) {
        return userRoleService.findRoleGroupByUser(pageRequestDTO);
    }

    /**
     * 사원의 권한그룹을 해제합니다.
     * 회사 또는 부서등의 상위에 부여된 권한그룹은 삭제되지 않습니다.
     * @param removeData - 권한그룹을 해제할 사원의 orga_no 목록 전달
     * @return UserRoleResponseDTO - 결과의 상태(state), 제목(title), 메시지(message)를 담은 객체 반환
     * @author 박준성
     */
    @Operation(summary = "부여된 권한그룹 제거", description = "선택한 사용자에 부여된 권한그룹 중 본인에게 부여된 권한그룹만 제거 가능합니다.")
    @PostMapping("/removeOrgaRoleByAllRole")
    public UserRoleResponseDTO removeUserRole(@RequestBody List<UserRoleReqRemoveUserRoleDTO> removeData){
        UserRoleResponseDTO result = UserRoleResponseDTO.builder().build();
        try {
            result = userRoleService.removeOrgaRole(removeData);
        }catch (RuntimeException re){
            result.setState("error");
            result.setTitle("삭제 실패");
            result.setMessage(re.getMessage());
        }catch(Exception e){
            result = UserRoleResponseDTO
                    .builder()
                    .state("error")
                    .title("삭제 실패")
                    .message("관리자에게 문의해주세요.")
                    .build();
        }
        return result;
    }
}
