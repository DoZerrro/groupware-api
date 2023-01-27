package com.dq.aquaranth.userrole.mapper;

import com.dq.aquaranth.userrole.dto.paging.PageRequestDTO;
import com.dq.aquaranth.userrole.dto.request.*;
import com.dq.aquaranth.userrole.dto.response.*;

import java.util.List;

public interface UserRoleMapper {

    /** GroupList **/

    List<UserRoleCompanyDTO> findCompanyAll();
    List<UserRoleCompanyDTO> findCompany(String username);

    List<UserRoleRoleGroupBasedListDTO> findRoleGroupByOrgaNo(PageRequestDTO pageRequestDTO);
    Integer findRoleGroupTotalByOrgaNo(PageRequestDTO pageRequestDTO);

    List<UserRoleGroupBasedUserListDTO> findOrgaByRoleGroupNo(PageRequestDTO pageRequestDTO);
    Integer findOrgaTotalByRoleGroupNo(PageRequestDTO pageRequestDTO);

    Integer insertUserRole(UserRoleReqInsertOrgaRoleDTO userRoleReqInsertOrgaRoleDTO);

    Integer removeOrgaRole(UserRoleReqRemoveOrgaRoleDTO userRoleReqRemoveOrgaRoleDTO);

    /** UserList **/

    List<UserRoleUserListBasedDTO> findUserListByOrgaNo(PageRequestDTO pageRequestDTO);
    Integer findUserListTotalByOrgaNo(PageRequestDTO pageRequestDTO);

    List<UserRoleUserBasedRoleGroupDTO> findRoleGroupByUser(PageRequestDTO pageRequestDTO);
    Integer findRoleGroupTotalByUser(PageRequestDTO pageRequestDTO);

    Integer removeUserRole(UserRoleReqRemoveUserRoleDTO2 userRoleReqRemoveOrgaRoleDTO2);

    Integer deleteAllByRoleGroupNo(Long roleGroupNo);
}

