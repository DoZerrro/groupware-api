package com.dq.aquaranth.mygroup.controller;

import com.dq.aquaranth.menu.annotation.MenuCode;
import com.dq.aquaranth.mygroup.dto.mygroup.MygroupInformationDTO;
import com.dq.aquaranth.mygroup.dto.mygroup.MygroupListDTO;
import com.dq.aquaranth.mygroup.dto.mygroup.MygroupUpdateDTO;
import com.dq.aquaranth.mygroup.service.mygroup.MygroupService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/api/mygroup")
@MenuCode
public class MygroupController {
    private final MygroupService mygroupService;

    /**
     * 마이그룹
     */
    @GetMapping("/list")
    public List<MygroupListDTO> getMygroupList(Authentication authentication) {
        log.info("로그인한 사원의 마이그룹 전체 조회");
        // 로그인한 사원의 아이디를 받아 저장
        String username = authentication.getName();
        log.info(username);
        return mygroupService.findAllMygroup(username);
    }

    @GetMapping("/information/{mygroupNo}")
    public MygroupInformationDTO getMygroupInformation(@PathVariable Long mygroupNo) {
        log.info("해당 마이그룹 조회");
        return mygroupService.findByMygroupNo(mygroupNo);
    }

    @PostMapping("/register")
    public Long registerMygroup(Authentication authentication) {
        log.info("로그인한 사원의 마이그룹 생성");
        String regUser = authentication.getName();
        return mygroupService.insert(regUser);
    }

    @PutMapping("/modify/{mygroupNo}")
    public Long modifyMygroup(@RequestBody MygroupUpdateDTO mygroupUpdateDTO, Authentication authentication) {
        log.info("로그인한 사원의 마이그룹 이름 수정");
        String modUser = authentication.getName();
        mygroupUpdateDTO.setModUser(modUser);
        return mygroupService.update(mygroupUpdateDTO);
    }

    @DeleteMapping("/remove/{mygroupNo}")
    public Long removeMygroup(@PathVariable Long mygroupNo) {
        log.info("해당 마이그룹 삭제(즐겨찾기된 사원 먼저 삭제 후 실행)");
        return mygroupService.delete(mygroupNo);
    }
}
