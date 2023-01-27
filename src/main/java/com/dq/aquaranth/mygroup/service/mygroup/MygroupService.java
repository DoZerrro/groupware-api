package com.dq.aquaranth.mygroup.service.mygroup;

import com.dq.aquaranth.mygroup.dto.mygroup.MygroupInformationDTO;
import com.dq.aquaranth.mygroup.dto.mygroup.MygroupListDTO;
import com.dq.aquaranth.mygroup.dto.mygroup.MygroupUpdateDTO;

import java.util.List;

public interface MygroupService {

    /**
     * 마이그룹
     */
    List<MygroupListDTO> findAllMygroup(String username);
    MygroupInformationDTO findByMygroupNo(Long mygroupNo);
    Long insert(String regUser);
    Long update(MygroupUpdateDTO mygroupUpdateDTO);
    Long delete(Long mygroupNo);
}
