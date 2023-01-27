package com.dq.aquaranth.mygroup.mapper;

import com.dq.aquaranth.mygroup.dto.favorite.FavoriteEmpListDTO;
import com.dq.aquaranth.mygroup.dto.favorite.FavoriteInsertDTO;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;


@SpringBootTest
@Log4j2
class FavoriteeMapperTest {

    @Autowired(required = false)
    FavoriteeMapper favoriteeMapper;

    @Test
    @DisplayName("해당 마이그룹에 사원 즐겨찾기")
    void insert() {
        Long mygroupNo = 13L;
        Long orgaNo = 35L;
        String username = "emp10";
        FavoriteInsertDTO favoriteInsertDTO = FavoriteInsertDTO
                .builder()
                .mygroupNo(mygroupNo)
                .orgaNo(orgaNo)
                .regUser(username)
                .build();
        Long insertResult = favoriteeMapper.insert(favoriteInsertDTO);
        log.info("즐겨찾기 한 결과 : " + insertResult);
    }

    @Test
    @DisplayName("해당 마이그룹에 즐겨찾기 된 모든 사원 정보 출력")
    void findAll() {
        Long mygroupNo = 13L;
        List<FavoriteEmpListDTO> favoriteEmpListDTO
                = favoriteeMapper.findAll(mygroupNo);
        favoriteEmpListDTO.forEach(log::info);
    }

    @Test
    @DisplayName("해당 마이그룹에 즐겨찾기 된 사원 삭제")
    void delete() {
        Long mygroupNo = 13L;
        Long orgaNo = 35L;
        Long deleteResult = favoriteeMapper.delete(mygroupNo, orgaNo);
        log.info("즐겨찾기에서 삭제한 결과 : " + deleteResult);
    }
}
