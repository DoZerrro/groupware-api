package com.dq.aquaranth.login.handler;

import com.dq.aquaranth.login.service.RedisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
@Log4j2
public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {
    private final RedisService redisService;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {


        System.out.println("@@@@@@@@@@@@@@@@@@@@@" + request.getHeader("Authorization"));
        System.out.println("#################" + authentication);




//        log.info("{}님이 로그아웃 하였습니다.", username);
//        redisService.deleteObject(username);

    }
}
