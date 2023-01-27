package com.dq.aquaranth.menu.service;

import com.dq.aquaranth.menu.dto.request.MenuQueryDTO;
import com.dq.aquaranth.menu.dto.response.MenuImportResponseDTO;
import com.dq.aquaranth.menu.dto.response.MenuTreeResponseDTO;

import java.util.List;

public interface MenuService {

    List<MenuTreeResponseDTO> findAllBy(MenuQueryDTO menuQueryDTO, String username);

    List<MenuImportResponseDTO> initRoutes();
}




