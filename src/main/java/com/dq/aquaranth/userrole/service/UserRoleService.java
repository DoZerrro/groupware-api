package com.dq.aquaranth.userrole.service;

import com.dq.aquaranth.userrole.dto.paging.PageRequestDTO;
import com.dq.aquaranth.userrole.dto.paging.PageResponseDTO;
import com.dq.aquaranth.userrole.dto.request.*;
import com.dq.aquaranth.userrole.dto.response.*;

import java.util.List;

public interface UserRoleService {

    /** GroupList **/

    List<UserRoleCompanyDTO> findCompanyAll();

    List<UserRoleCompanyDTO> findCompany(String username);

    PageResponseDTO<UserRoleRoleGroupBasedListDTO> findRoleGroupByOrgaNo(PageRequestDTO pageRequestDTO);

    PageResponseDTO<UserRoleGroupBasedUserListDTO> findOrgaByRoleGroupNo(PageRequestDTO pageRequestDTO);

    Integer insertUserRole(UserRoleReqInsertOrgaRoleDTO userRoleReqInsertOrgaRoleDTO);

    UserRoleResponseDTO removeOrgaRole(UserRoleReqRemoveOrgaRoleDTO userRoleReqRemoveOrgaRoleDTO);

    /** UserList **/

    PageResponseDTO<UserRoleUserListBasedDTO> findUserListByOrgaNo(PageRequestDTO pageRequestDTO);

    PageResponseDTO<UserRoleUserBasedRoleGroupDTO> findRoleGroupByUser(PageRequestDTO pageRequestDTO);

    UserRoleResponseDTO removeOrgaRole(List<UserRoleReqRemoveUserRoleDTO> removeData);
}
