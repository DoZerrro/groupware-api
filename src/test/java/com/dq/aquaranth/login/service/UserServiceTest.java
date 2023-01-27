package com.dq.aquaranth.login.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Log4j2
class UserServiceTest {
    @Autowired
    private UserService userService;

    @Test
    @DisplayName("사용자가 로그인 요청시, database 에 없는 사원이라면 예외를 발생시킵니다.")
    void notExistUserTryLoginThrowsException() {
        // given
        String notExistUsername = "aklfdg";

        // when & then
        assertThrows(UsernameNotFoundException.class, () -> userService.loadUserByUsername(notExistUsername));
    }
}
