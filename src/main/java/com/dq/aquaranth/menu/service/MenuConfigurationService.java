package com.dq.aquaranth.menu.service;

import com.dq.aquaranth.menu.dto.request.MenuInsertDTO;
import com.dq.aquaranth.menu.dto.request.MenuQueryDTO;
import com.dq.aquaranth.menu.dto.request.MenuUpdateDTO;
import com.dq.aquaranth.menu.dto.response.MenuDetailResponseDTO;
import com.dq.aquaranth.menu.dto.response.MenuResponseDTO;
import com.dq.aquaranth.menu.dto.response.MenuTreeResponseDTO;
import com.dq.aquaranth.menu.objectstorage.dto.request.MultipartFileDTO;

import java.util.List;

public interface MenuConfigurationService {

    MenuResponseDTO findBy(MenuQueryDTO menuQueryDTO);

    List<MenuResponseDTO> findAllBy(MenuQueryDTO menuQueryDTO);

    List<MenuTreeResponseDTO> findUnderMenusBy(MenuQueryDTO menuQueryDTO);

    MenuDetailResponseDTO findMenuDetailsBy(MenuQueryDTO menuQueryDTO);

    Integer insert(MenuInsertDTO menuInsertDTO, String username) throws Exception;

    Integer update(MenuUpdateDTO menuUpdateDTO, String username);

    Integer updateIcon(MultipartFileDTO multipartFileDTO, String username) throws Exception;

    Integer delete(MenuQueryDTO menuQueryDTO);

}
