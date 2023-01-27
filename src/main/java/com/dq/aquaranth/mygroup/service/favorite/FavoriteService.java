package com.dq.aquaranth.mygroup.service.favorite;

import com.dq.aquaranth.mygroup.dto.favorite.FavoriteEmpListDTO;
import com.dq.aquaranth.mygroup.dto.favorite.FavoriteInsertDTO;

import java.util.List;

public interface FavoriteService {

    /**
     * 즐겨찾기
     */
    Long insert(FavoriteInsertDTO favoriteInsertDTO, String regUser);
    List<FavoriteEmpListDTO> findAll(Long mygroupNo);
    Long delete(Long mygroupNo, Long orgaNo);
}
