package com.dq.aquaranth.mygroup.controller;

import com.dq.aquaranth.menu.annotation.MenuCode;
import com.dq.aquaranth.mygroup.dto.favorite.FavoriteEmpListDTO;
import com.dq.aquaranth.mygroup.dto.favorite.FavoriteInsertDTO;
import com.dq.aquaranth.mygroup.service.favorite.FavoriteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/api/favorite")
@MenuCode
public class FavoriteController {

    private final FavoriteService favoriteService;

    /**
     * 즐겨찾기
     */
    @PostMapping("/register")
    public Long registerFavorite(@RequestBody FavoriteInsertDTO favoriteInsertDTO, Authentication authentication) {
        log.info("해당 마이그룹에 사원 즐겨찾기");
        // 로그인한 사원의 아이디
        String regUser = authentication.getName();
        favoriteInsertDTO.setRegUser(regUser);
        return favoriteService.insert(favoriteInsertDTO, regUser);
    }

    @GetMapping("/list/{mygroupNo}")
    public List<FavoriteEmpListDTO> getFavoriteEmpList(@PathVariable Long mygroupNo) {
        log.info("해당 마이그룹에 즐겨찾기 된 모든 사원 정보 출력");
        return favoriteService.findAll(mygroupNo);
    }

    @DeleteMapping("/remove/{mygroupNo}/{orgaNo}")
    public Long removeFavortieEmp(@PathVariable Long mygroupNo, @PathVariable Long orgaNo) {
        log.info("해당 마이그룹에 즐겨찾기 된 사원 삭제");
        return favoriteService.delete(mygroupNo, orgaNo);
    }
}
