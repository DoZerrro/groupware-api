package com.dq.aquaranth.mygroup.service.mygroup;

import com.dq.aquaranth.mygroup.dto.mygroup.MygroupInformationDTO;
import com.dq.aquaranth.mygroup.dto.mygroup.MygroupListDTO;
import com.dq.aquaranth.mygroup.dto.mygroup.MygroupUpdateDTO;
import com.dq.aquaranth.mygroup.mapper.MygroupMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class MygroupServiceImpl implements MygroupService {

    private final MygroupMapper mygroupMapper;

    /**
     * 마이그룹
     */
    @Override
    public List<MygroupListDTO> findAllMygroup(String username) {
        log.info("로그인한 사원의 마이그룹 전체 조회");
        return mygroupMapper.findAllMygroup(username);
    }

    @Override
    public MygroupInformationDTO findByMygroupNo(Long mygroupNo) {
        log.info("해당 마이그룹 조회");
        return mygroupMapper.findByMygroupNo(mygroupNo);
    }

    @Override
    public Long insert(String regUser) {
        log.info("로그인한 사원의 마이그룹 생성");
        return mygroupMapper.insert(regUser);
    }

    @Override
    public Long update(MygroupUpdateDTO mygroupUpdateDTO) {
        log.info("로그인한 사원의 마이그룹 이름 수정");
        return mygroupMapper.update(mygroupUpdateDTO);
    }

    @Override
    public Long delete(Long mygroupNo) {
        log.info("해당 마이그룹 삭제(즐겨찾기된 사원 먼저 삭제 후 실행)");
        return mygroupMapper.delete(mygroupNo);
    }
}
