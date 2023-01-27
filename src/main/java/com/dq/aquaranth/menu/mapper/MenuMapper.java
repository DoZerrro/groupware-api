package com.dq.aquaranth.menu.mapper;

import com.dq.aquaranth.menu.dto.request.MenuQueryDTO;
import com.dq.aquaranth.menu.dto.response.MenuImportResponseDTO;
import com.dq.aquaranth.menu.dto.response.MenuTreeResponseDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MenuMapper {
    List<MenuTreeResponseDTO> findAllBy(@Param("menu") MenuQueryDTO menuQueryDTO, @Param("rolegroup") List<Long> roleGroupNo);

    List<MenuImportResponseDTO> initRoutes();

}
