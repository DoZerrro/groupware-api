package com.dq.aquaranth.login.aop;

import com.dq.aquaranth.login.annotation.RedisUpdate;
import com.dq.aquaranth.login.constant.RedisKeys;
import com.dq.aquaranth.login.domain.LoginUser;
import com.dq.aquaranth.login.dto.LoginUserInfo;
import com.dq.aquaranth.login.service.RedisService;
import com.dq.aquaranth.login.service.UserSessionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * {@code @RedisUpdate} 어노테이션이 붙은 메서드가 실행되기 전 동작하는 AOP 입니다.
 *
 * @author jonghyun
 */
@Component
@Aspect
@RequiredArgsConstructor
@Log4j2
public class RedisUpdateAop {
    private final UserSessionService userSessionService;
    private final RedisService redisService;

    /**
     * point cut 을 지정할 패키지 경로입니다.
     */
    @Pointcut("execution(* com.dq.aquaranth..*.*(..))")
    private void cut() {
    }

    /**
     * pointcut 을 지정한 하위패키지에 있는 @RedisUpdate 어노테이션이 붙은 메서드가 실행된 이후에 동작합니다.
     */
    @After("cut() && @annotation(redisUpdate)")
    public void after(RedisUpdate redisUpdate) {
        // 접속한 모든유저의 레디스 정보 업데이트
        redisService.keys(RedisKeys.ROLE_KEY.getKey()).forEach(key -> {
            String username = key.replace(RedisKeys.ROLE_KEY.getKey(), "");
            LoginUserInfo loginUserInfo = userSessionService.findUserInfoInRedis(username);

            userSessionService.loadUserInfoByLoginUser(LoginUser.builder()
                    .loginDeptNo(loginUserInfo.getDept().getDeptNo())
                    .loginCompanyNo(loginUserInfo.getCompany().getCompanyNo())
                    .username(username)
                    .build());

            log.info("Redis 에 {}님의 정보가 업데이트 되었습니다.", username);
        });
    }
}
