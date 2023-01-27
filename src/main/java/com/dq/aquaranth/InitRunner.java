package com.dq.aquaranth;

import com.dq.aquaranth.login.service.RedisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * application 실행시 처리되는 로직입니다
 *
 * @author jonghyun
 */
@Component
@RequiredArgsConstructor
@Log4j2
public class InitRunner implements ApplicationRunner {
    private final RedisService redisService;

    /**
     * springboot load 시 실행됩니다.
     */
    @Override
    public void run(ApplicationArguments args) {
        initRedis();
    }

    /**
     * redis 에 기존에 저장된 데이터를 전부 삭제합니다.
     * 메뉴코드에 매핑되어있는 권한그룹들을 저장합니다.
     */
    public void initRedis() {
        log.info("Redis init");
        redisService.deleteObject(redisService.keys("*")); // 기존 데이터 삭제
        redisService.updateMenuRoles();
    }
}
