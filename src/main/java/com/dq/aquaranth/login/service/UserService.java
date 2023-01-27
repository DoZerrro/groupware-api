package com.dq.aquaranth.login.service;

import com.dq.aquaranth.emp.dto.emp.EmpDTO;
import com.dq.aquaranth.emp.mapper.EmpMapper;
import com.dq.aquaranth.login.domain.CustomUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 스프링 시큐리티 로그인 비즈니스 로직을 처리합니다.
 *
 * @author 임종현
 */
@Service
@RequiredArgsConstructor
@Transactional
@Log4j2
public class UserService implements UserDetailsService {
    private final EmpMapper empMapper;

    /**
     * /api/login 요청을 받으면 db 에서 사원정보를 검색합니다.
     *
     * @param username the username identifying the user whose data is required.
     * @return - 사원 객체와 조합된 UserDetails
     * @throws UsernameNotFoundException - login 파라미터로 전달받은 username 이 사원테이블에 존재하지 않을때 예외가 발생합니다.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("사용자가 로그인을 시도합니다. username => {}", username);
        EmpDTO empDTO = empMapper.findByUsername(username);

        if (empDTO == null) {
            log.error("사원 정보가 db에 존재하지 않습니다.");
            throw new UsernameNotFoundException("User not found in the database");
        }

        return new CustomUser(empDTO);
    }

}
