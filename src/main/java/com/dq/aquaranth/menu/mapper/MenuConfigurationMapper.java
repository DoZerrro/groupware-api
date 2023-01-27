package com.dq.aquaranth.menu.mapper;

import com.dq.aquaranth.menu.dto.request.*;
import com.dq.aquaranth.menu.dto.response.MenuDetailResponseDTO;
import com.dq.aquaranth.menu.dto.response.MenuResponseDTO;
import com.dq.aquaranth.menu.dto.response.MenuTreeResponseDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface MenuConfigurationMapper {
    Optional<MenuResponseDTO> findBy(MenuQueryDTO menuQueryDTO);

    List<MenuResponseDTO> findAllBy(MenuQueryDTO menuQueryDTO);

    List<MenuTreeResponseDTO> findUnderMenusBy(MenuQueryDTO menuQueryDTO);

    Optional<MenuDetailResponseDTO> findMenuDetailsBy(MenuQueryDTO menuQueryDTO);

    Integer insert(MenuInsertDTO menuUpdateDTO);

    Integer update(MenuUpdateDTO menuUpdateDTO);

    Integer updateIcon(MenuIconUpdateDTO menuIconUpdateDTO);

    Integer delete(MenuQueryDTO menuQueryDTO);
}
