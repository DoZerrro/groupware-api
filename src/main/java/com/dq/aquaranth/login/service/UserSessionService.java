package com.dq.aquaranth.login.service;

import com.dq.aquaranth.company.dto.CompanyInformationDTO;
import com.dq.aquaranth.login.domain.LoginUser;
import com.dq.aquaranth.login.dto.LoginUserInfo;

import java.util.Map;

/**
 * login 된 유저세션에 관련된 비즈니스 로직을 처리합니다.
 *
 * @author 임종현
 */
public interface UserSessionService {

    /**
     * 접속한 사원의 정보를 redis 에 요청합니다.
     *
     * @param username - 로그인된 사원의 id
     * @return - 접속한 사원의 조직,권한 정보가 담긴 객체
     */
    LoginUserInfo findUserInfoInRedis(String username);

    /**
     * refresh token 을 검증합니다.
     *
     * @param authorizationHeader - 토큰을 검사할 http header
     * @return - 검증완료시 반환할 access, refresh token
     */
    Map<String, String> checkRefresh(String authorizationHeader);

    /**
     * 사용자가 로그인을 한 후 접속할 회사, 부서를 선택하면 호출되어 redis 에 정보를 저장합니다.
     * @param loginUser : 접속한 사원이 소속된 조직정보가 담긴 객체입니다.
     * @return redis에 저장된 dto 객체입니다.
     */
    LoginUserInfo loadUserInfoByLoginUser(LoginUser loginUser);

    /**
     * 접속한 사원의 회사정보를 요청합니다.
     *
     * @param username - 접속한 사원의 id
     * @return - 회사정보
     */
    CompanyInformationDTO findLoginUserCompany(String username);
}
