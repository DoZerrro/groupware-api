package com.dq.aquaranth.mygroup.mapper;

import com.dq.aquaranth.mygroup.dto.favorite.FavoriteEmpListDTO;
import com.dq.aquaranth.mygroup.dto.favorite.FavoriteInsertDTO;

import java.util.List;

public interface FavoriteeMapper {

    /**
     * 해당 마이그룹에 사원 즐겨찾기
     */
    Long insert (FavoriteInsertDTO favoriteInsertDTO);

    /**
     * 해당 마이그룹에 즐겨찾기 된 모든 사원 정보 출력
     */
    List<FavoriteEmpListDTO> findAll(Long mygroupNo);

    /**
     * 해당 마이그룹에 즐겨찾기 된 사원 삭제
     */
    Long delete(Long mygroupNo, Long orgaNo);
}
