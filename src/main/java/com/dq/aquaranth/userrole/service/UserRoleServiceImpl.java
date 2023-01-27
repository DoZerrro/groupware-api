package com.dq.aquaranth.userrole.service;

import com.dq.aquaranth.userrole.dto.paging.PageRequestDTO;
import com.dq.aquaranth.userrole.dto.paging.PageResponseDTO;
import com.dq.aquaranth.userrole.dto.request.*;
import com.dq.aquaranth.userrole.dto.response.*;
import com.dq.aquaranth.userrole.mapper.UserRoleMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class UserRoleServiceImpl implements UserRoleService {

    /** GroupList **/

    private final UserRoleMapper userRoleMapper;

    @Override
    public List<UserRoleCompanyDTO> findCompanyAll() {
        return userRoleMapper.findCompanyAll();
    }

    @Override
    public List<UserRoleCompanyDTO> findCompany(String username) {
        return userRoleMapper.findCompany(username);
    }

    public PageResponseDTO<UserRoleRoleGroupBasedListDTO> findRoleGroupByOrgaNo(PageRequestDTO pageRequestDTO) {
        List<UserRoleRoleGroupBasedListDTO> list = userRoleMapper.findRoleGroupByOrgaNo(pageRequestDTO);

        Integer total = userRoleMapper.findRoleGroupTotalByOrgaNo(pageRequestDTO);

        return new PageResponseDTO<>(pageRequestDTO, total == null ? 0 : total, list);
    }

    @Override
    public PageResponseDTO<UserRoleGroupBasedUserListDTO> findOrgaByRoleGroupNo(PageRequestDTO pageRequestDTO) {
        List<UserRoleGroupBasedUserListDTO> list = userRoleMapper.findOrgaByRoleGroupNo(pageRequestDTO);

        Integer total = userRoleMapper.findOrgaTotalByRoleGroupNo(pageRequestDTO);

        return new PageResponseDTO<>(pageRequestDTO, total == null ? 0 : total, list);
    }

    @Override
    public Integer insertUserRole(UserRoleReqInsertOrgaRoleDTO userRoleReqInsertOrgaRoleDTO) throws RuntimeException{
        if(userRoleReqInsertOrgaRoleDTO.getOrgaNoList().size() == 0) throw new RuntimeException("권한그룹 처리 대상 없음");
        return userRoleMapper.insertUserRole(userRoleReqInsertOrgaRoleDTO);
    }

    @Override
    public UserRoleResponseDTO removeOrgaRole(UserRoleReqRemoveOrgaRoleDTO userRoleReqRemoveOrgaRoleDTO) throws RuntimeException {
        int beforeListSIze = userRoleReqRemoveOrgaRoleDTO.getRemoveOrgaRoleList().size();
        Integer result = userRoleMapper.removeOrgaRole(userRoleReqRemoveOrgaRoleDTO);

        if(beforeListSIze != result) throw new RuntimeException("권한그룹 처리 실패");

        return UserRoleResponseDTO
                .builder()
                .title("삭제 완료")
                .message("권한그룹이 해제되었습니다.")
                .state("success")
                .build();
    }

    /** UserList **/

    @Override
    @Transactional
    public PageResponseDTO<UserRoleUserListBasedDTO> findUserListByOrgaNo(PageRequestDTO pageRequestDTO) {
        List<UserRoleUserListBasedDTO> list = userRoleMapper.findUserListByOrgaNo(pageRequestDTO);

        Integer total = userRoleMapper.findUserListTotalByOrgaNo(pageRequestDTO);

        return new PageResponseDTO<>(pageRequestDTO, total == null ? 0 : total, list);
    }

    @Override
    public PageResponseDTO<UserRoleUserBasedRoleGroupDTO> findRoleGroupByUser(PageRequestDTO pageRequestDTO) {
        List<UserRoleUserBasedRoleGroupDTO> list = userRoleMapper.findRoleGroupByUser(pageRequestDTO);

        Integer total = userRoleMapper.findRoleGroupTotalByUser(pageRequestDTO);

        return new PageResponseDTO<>(pageRequestDTO, total == null ? 0 : total, list);
    }

    @Override
    @Transactional(rollbackFor = {RuntimeException.class})
    public UserRoleResponseDTO removeOrgaRole(List<UserRoleReqRemoveUserRoleDTO> removeData) throws RuntimeException {
        UserRoleResponseDTO result = UserRoleResponseDTO.builder().build();
        AtomicBoolean flag = new AtomicBoolean(false);
        AtomicReference<Long> targetOrgaNo = new AtomicReference<>((long) 0);

        removeData.forEach(dto -> {
            targetOrgaNo.set(dto.getTargetOrgaNo());
            if(!Objects.equals(dto.getOrgaNo(), dto.getTargetOrgaNo())) flag.set(true);
        });

        if(flag.get()){
            result.setState("error");
            result.setTitle("권한그룹 해제 실패");
            result.setMessage("해당 사용자에 부여된\n권한이 아닙니다.");
        }else{
            List<Long> removeUserRoleList = removeData.stream()
                    .filter(dto -> Objects.equals(dto.getTargetOrgaNo(), dto.getOrgaNo()))
                    .map(UserRoleReqRemoveUserRoleDTO::getRoleGroupNo)
                    .collect(Collectors.toList());

            int beforeSize = removeUserRoleList.size();

            UserRoleReqRemoveUserRoleDTO2 userRoleReqRemoveUserRoleDTO2 = UserRoleReqRemoveUserRoleDTO2.builder()
                    .targetOrgaNo(targetOrgaNo.get())
                    .removeUserRoleList(removeUserRoleList)
                    .build();

            Integer afterSize = userRoleMapper.removeUserRole(userRoleReqRemoveUserRoleDTO2);

            log.info("beforeSize : " + beforeSize);
            log.info("afterSize : " + afterSize);

            if(afterSize != beforeSize) throw new RuntimeException("권한그룹 삭제에 실패했습니다");

            result.setState("success");
            result.setTitle("권한그룹 해제 완료");
            result.setMessage("선택한 권한그룹이\n해제되었습니다.");
        }

        return result;
    }
}
