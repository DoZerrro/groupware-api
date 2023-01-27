package com.dq.aquaranth.login.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.dq.aquaranth.commons.utils.JWTUtil;
import com.dq.aquaranth.company.dto.CompanyInformationDTO;
import com.dq.aquaranth.company.mapper.CompanyMapper;
import com.dq.aquaranth.dept.mapper.DeptMapper;
import com.dq.aquaranth.emp.mapper.EmpMapper;
import com.dq.aquaranth.emp.mapper.EmpMappingMapper;
import com.dq.aquaranth.login.constant.RedisKeys;
import com.dq.aquaranth.login.domain.LoginUser;
import com.dq.aquaranth.login.dto.LoginUserInfo;
import com.dq.aquaranth.menu.dto.request.MenuQueryDTO;
import com.dq.aquaranth.menu.dto.response.MenuResponseDTO;
import com.dq.aquaranth.menu.exception.ErrorCodes;
import com.dq.aquaranth.menu.exception.CommonException;
import com.dq.aquaranth.rolegroup.domain.RoleGroup;
import com.dq.aquaranth.rolegroup.mapper.RoleGroupMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static com.dq.aquaranth.login.jwt.JwtProperties.*;

@Service
@RequiredArgsConstructor
@Log4j2
public class UserSessionServiceImpl implements UserSessionService {
    private final RedisTemplate<String, Object> redisTemplate;
    private final JWTUtil jwtUtil;
    private final RoleGroupMapper roleGroupMapper;
    private final CompanyMapper companyMapper;
    private final EmpMapper empMapper;
    private final DeptMapper deptMapper;
    private final EmpMappingMapper empMappingMapper;

    @Override
    public LoginUserInfo findUserInfoInRedis(String username) {
        String value = (String) redisTemplate.opsForValue().get(RedisKeys.USER_KEY.getKey() + username);
        if (Objects.isNull(value)) {
            log.error("redis 에 유저정보가 존재하지 않습니다. username => {}", username);
            throw new CommonException(ErrorCodes.REDIS_USER_NOT_FOUND);
        }

        ObjectMapper mapper = new ObjectMapper()
                .registerModule(new JavaTimeModule())
                .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        LoginUserInfo userInfo;

        try {
            userInfo = mapper.readValue(value, LoginUserInfo.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return userInfo;
    }

    @Override
    public Map<String, String> checkRefresh(String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith(TOKEN_PREFIX)) {
            log.info("refresh token 을 검증합니다.");
            String refreshToken = authorizationHeader.substring(TOKEN_PREFIX.length());
            Algorithm algorithm = Algorithm.HMAC256(SECRET.getBytes()); // 토큰 생성할 때와 같은 알고리즘으로 풀어야함.
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT decodedJWT;

            try {
                decodedJWT = verifier.verify(refreshToken);
            } catch (TokenExpiredException | JWTDecodeException verificationException) {
                throw new CommonException(ErrorCodes.REFRESH_TOKEN_EXPIRED);
            }

            // 토큰이 유효한지 확인되면, 사용자의 이름을 가져올 수 있습니다.
            String username = decodedJWT.getSubject(); // token 과 함께 제공되는 사용자 이름을 줍니다.
            log.info("refresh token 검증이 완료되었습니다.");
            return jwtUtil.generateToken(username);
        }
        throw new CommonException(ErrorCodes.TOKEN_MISSING);
    }

    /**
     * 사용자가 로그인을 한 후 접속할 회사, 부서를 선택하면 호출되어 redis 에 정보를 저장합니다.
     * @param loginUser : 접속한 사원이 소속된 조직정보가 담긴 객체입니다.
     * @return redis에 저장된 dto 객체입니다.
     */
    @Override
    public LoginUserInfo loadUserInfoByLoginUser(LoginUser loginUser) {
        LoginUserInfo redisDTO = LoginUserInfo.builder()
                .emp(empMapper.findByUsername(loginUser.getUsername()))
                .empMapping(empMappingMapper.findByUsernameAndDeptNo(loginUser.getUsername(), loginUser.getLoginDeptNo()))
                .company(companyMapper.findByCompanyNo(loginUser.getLoginCompanyNo()))
                .dept(deptMapper.select(loginUser.getLoginDeptNo()))
                .company(companyMapper.findByCompanyNo(loginUser.getLoginCompanyNo()))
                .roleGroups(roleGroupMapper.findRoleGroupsByLoginUser(loginUser))
                .build();

        ObjectMapper objectMapper = new ObjectMapper();
        String loginUserInfo;

        try {
            loginUserInfo = objectMapper.registerModule(new JavaTimeModule()).writeValueAsString(redisDTO);
        } catch (JsonProcessingException e) {
            log.error("redis에 저장할 객체를 직렬화 하던중 예외가 발생했습니다.");
            throw new RuntimeException(e);
        }

        log.info("redis에 유저 정보를 저장합니다. {} 초 후 만료됨.",REFRESH_TOKEN_EXPIRATION_TIME);
        log.info("저장된 유저정보 key: {}, value: {}", RedisKeys.USER_KEY.getKey() + loginUser.getUsername(), loginUserInfo);
        redisTemplate.opsForValue().set(
                RedisKeys.USER_KEY.getKey() + loginUser.getUsername(),
                loginUserInfo,
                REFRESH_TOKEN_EXPIRATION_TIME,
                TimeUnit.MILLISECONDS
        );

        return redisDTO;
    }

    @Override
    public CompanyInformationDTO findLoginUserCompany(String username) {
        return findUserInfoInRedis(username).getCompany();
    }

}
