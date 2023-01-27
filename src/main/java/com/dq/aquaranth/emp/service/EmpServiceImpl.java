package com.dq.aquaranth.emp.service;

import com.dq.aquaranth.emp.dto.emp.*;
import com.dq.aquaranth.emp.dto.login.EmpRedisDTO;
import com.dq.aquaranth.emp.dto.login.EmpLoginEmpDTO;
import com.dq.aquaranth.emp.dto.login.EmpUpdateRecentAccessDTO;
import com.dq.aquaranth.emp.dto.orga.EmpOrgaDTO;
import com.dq.aquaranth.emp.dto.orga.EmpOrgaSelectDTO;
import com.dq.aquaranth.emp.dto.orga.EmpOrgaUpdateListDTO;
import com.dq.aquaranth.emp.mapper.EmpMapper;
import com.dq.aquaranth.emp.mapper.EmpMappingMapper;
import com.dq.aquaranth.login.service.UserSessionService;
import com.dq.aquaranth.menu.objectstorage.dto.request.MultipartFileDTO;
import com.dq.aquaranth.menu.objectstorage.dto.request.ObjectGetRequestDTO;
import com.dq.aquaranth.menu.objectstorage.dto.request.ObjectPostRequestDTO;
import com.dq.aquaranth.menu.objectstorage.service.ObjectStorageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.management.openmbean.KeyAlreadyExistsException;
import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
@Log4j2
public class EmpServiceImpl implements EmpService {

    private final EmpMapper empMapper;
    private final EmpMappingMapper empMappingMapper;
    private final PasswordEncoder passwordEncoder;
    private final ObjectStorageService objectStorageService;
    private final UserSessionService userSessionService;


    // 사원

    @Override
    public List<EmpDTO> findAll() {
        List<EmpDTO> empDTOList = empMapper.findAll();

        empDTOList.forEach(empDTO -> {
            // 파일이 있는 사원만 profileUrl 생성
            if(empDTO.getUuid() != null && empDTO.getFilename() != null){
                ObjectGetRequestDTO objectRequestDTO = ObjectGetRequestDTO.builder()
                        .filename(empDTO.getUuid() + empDTO.getFilename())
                        .build();
                try {
                    empDTO.setProfileUrl(objectStorageService.getObject(objectRequestDTO).getUrl());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
        return empDTOList;
    }

    @Override
    public EmpDTO findById(Long empNo) {
        EmpDTO empDTO = empMapper.findById(empNo);

        if(empDTO.getUuid() != null && empDTO.getFilename() != null) {
            ObjectGetRequestDTO objectRequestDTO = ObjectGetRequestDTO.builder()
                    .filename(empDTO.getUuid() + empDTO.getFilename())
                    .build();
            try {
                empDTO.setProfileUrl(objectStorageService.getObject(objectRequestDTO).getUrl());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return empDTO;
    }

    @Override
    @Transactional
    public EmpDTO insert(EmpOrgaDTO orgaReqDTO, EmpDTO empReqDTO, EmpMappingDTO mapperReqDTO) throws IllegalAccessException {
        // 이미 가입된 유저
        if (Objects.nonNull(empMapper.findByUsername(empReqDTO.getUsername()))) {
            log.error("이미 가입된 유저입니다. username -> " + empReqDTO.getUsername());
            throw new KeyAlreadyExistsException("이미 가입된 유저입니다. username -> " + empReqDTO.getUsername());
        }

        // 조직 테이블 insert
        empMapper.insertOrga(orgaReqDTO);

        // 조직 테이블의 last_insert_id 저장
        Long orgaNo = orgaReqDTO.getOrgaNo();

        // 사원 테이블 insert
        empReqDTO.setPassword(passwordEncoder.encode(empReqDTO.getPassword()));
        empMapper.insert(empReqDTO);

        // 사원 테이블의 last_insert_id 저장
        Long empNo = empReqDTO.getEmpNo();

        // 사원매핑 테이블 insert
        mapperReqDTO.setOrgaNo(orgaNo);
        mapperReqDTO.setEmpNo(empNo);
        empMappingMapper.empMappingInsert(mapperReqDTO);

        return empReqDTO;
    }

    @Override
    public Long update(EmpUpdateDTO empUpdateDTO)
    {
        Long empNo = empUpdateDTO.getEmpNo();
        if(empUpdateDTO.getLastRetiredDate() != null){
            empMappingMapper.updateEmpMappingRetiredDate(empNo);
        }

        return empMapper.update(empUpdateDTO);
    }

    @Override
    @Transactional
    public Long updateFile(MultipartFileDTO multipartFileDTO) throws Exception {
        String uuid = UUID.randomUUID().toString();
        String filename = multipartFileDTO.getMultipartFile().getOriginalFilename();

        ObjectPostRequestDTO objectPostRequestDTO = ObjectPostRequestDTO.builder().filename(uuid + filename).multipartFile(multipartFileDTO.getMultipartFile()).build();

        EmpFileDTO empFileDTO = EmpFileDTO.builder().empNo(Long.valueOf(multipartFileDTO.getKey())).uuid(uuid).filename(filename).build();

        empMapper.updateProfile(empFileDTO);
        objectStorageService.postObject(objectPostRequestDTO);

        return empMapper.updateProfile(empFileDTO);
    }

    @Override
    public Long deleteProfile(Long empNo) {
        EmpFileDTO empFileDTO = EmpFileDTO
                .builder()
                .uuid(null)
                .filename(null)
                .empNo(empNo)
                .build();

        return empMapper.updateProfile(empFileDTO);
    }


    // 사원 회사, 부서(조직)

    @Override
    public List<EmpOrgaSelectDTO> findOrgaById(Long empNo) {
        return empMapper.findOrgaById(empNo);
    }

    @Override
    @Transactional
    public Long empOrgaInsert(EmpOrgaDTO orgaReqDTO, EmpMappingDTO mapperReqDTO, Long empNo) {
        // 조직 테이블 insert
        empMapper.insertOrga(orgaReqDTO);

        // 조직 테이블의 last_insert_id 저장
        Long orgaNo = orgaReqDTO.getOrgaNo();

        // 사원매핑 테이블 insert
        mapperReqDTO.setOrgaNo(orgaNo);
        mapperReqDTO.setEmpNo(empNo);

        return empMappingMapper.empMappingInsert(mapperReqDTO);
    }

    @Override
    public Long updateOrga(EmpOrgaUpdateListDTO orgaUpdateListDTO, String modUser) {
        Long orgaList = 0L;
        // 목록에 modUser 저장
        orgaUpdateListDTO.getList().forEach(orga -> orga.setModUser(modUser));

        // 리스트 정보들 하나씩 업데이트
        for (int i = 0; i < orgaUpdateListDTO.getList().size(); i++) {
            orgaList += empMapper.updateOrga(orgaUpdateListDTO.getList().get(i));
        }

        return orgaList;
    }


    // 접속 중인 사원

    @Override
    public List<EmpLoginEmpDTO> findLoginUser(String username) {
        String ip = null;

        try {
            ip = Inet4Address.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }

        // 접속한 아이디로 로그인한 사원의 정보를 찾아 데이터 저장
        List<EmpLoginEmpDTO> loginEmpList = empMapper.findLoginUser(username);

        String finalIp = ip;

        EmpDTO empDTO = empMapper.findByUsername(username);

        if(empDTO.getUuid() != null && empDTO.getFilename() != null) {
            ObjectGetRequestDTO objectRequestDTO = ObjectGetRequestDTO.builder()
                    .filename(empDTO.getUuid() + empDTO.getFilename())
                    .build();

            try {
                empDTO.setProfileUrl(objectStorageService.getObject(objectRequestDTO).getUrl());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        // 현재 접속한 ip와 해당 사원의 profileUrl을 사원 정보로 저장
        loginEmpList.forEach(emp -> {
            emp.setLoginIp(finalIp);
            emp.setProfileUrl(empDTO.getProfileUrl());
        });

        return loginEmpList;
    }


    @Override
    public EmpRedisDTO findLoggingInformation(String username) {
        Long company = userSessionService.findUserInfoInRedis(username).getCompany().getCompanyNo();
        String companyName = userSessionService.findUserInfoInRedis(username).getCompany().getCompanyName();
        Long dept = userSessionService.findUserInfoInRedis(username).getDept().getDeptNo();
        String deptName = userSessionService.findUserInfoInRedis(username).getDept().getDeptName();
        String empRank = userSessionService.findUserInfoInRedis(username).getEmpMapping().getEmpRank();
        Long orgaNo = userSessionService.findUserInfoInRedis(username).getEmpMapping().getOrgaNo();
        String hierarchy = empMapper.functionHierarchy(orgaNo);

        // 현재 접속한 사원의 회사, 부서, 조직에 속한 사원의 정보
        EmpRedisDTO empRedisDTO = EmpRedisDTO.builder()
                .loginCompany(company)
                .loginCompanyName(companyName)
                .loginDept(dept)
                .loginDeptName(deptName)
                .loginEmpRank(empRank)
                .hierarchy(hierarchy)
                .build();

        return empRedisDTO;
    }

    @Override
    public Long updateRecentAccessInfo(String username) {
        String ip = null;

        try {
            ip = Inet4Address.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }

        Long empNo = userSessionService.findUserInfoInRedis(username).getEmp().getEmpNo();

        // 최근 접속 ip, 시간 업데이트
        EmpUpdateRecentAccessDTO updateDTO
                = EmpUpdateRecentAccessDTO.builder()
                .lastLoginIp(ip)
                .lastLoginTime(LocalDateTime.now())
                .empNo(empNo)
                .build();

        return empMapper.updateRecentAccessInfo(updateDTO);
    }
}
