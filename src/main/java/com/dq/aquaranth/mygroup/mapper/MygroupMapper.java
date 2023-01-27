package com.dq.aquaranth.mygroup.mapper;

import com.dq.aquaranth.mygroup.dto.mygroup.MygroupInformationDTO;
import com.dq.aquaranth.mygroup.dto.mygroup.MygroupListDTO;
import com.dq.aquaranth.mygroup.dto.mygroup.MygroupUpdateDTO;

import java.util.List;

public interface MygroupMapper {

    /**
     * 로그인한 사원의 마이그룹 전체 조회
     */
    List<MygroupListDTO> findAllMygroup(String username);

    /**
     * 해당 마이그룹 조회
     */
    MygroupInformationDTO findByMygroupNo(Long mygroupNo);

    /**
     * 로그인한 사원의 마이그룹 생성
     */
    Long insert(String regUser);

    /**
     * 로그인한 사원의 마이그룸 이름 수정
     */
    Long update(MygroupUpdateDTO mygroupUpdateDTO);

    /**
     * 로그인한 사원의 마이그룹 삭제(해당 마이그룹 안에 즐겨찾기된 사원도 모두 삭제)
     */
    Long delete(Long mygroupNo);
}
