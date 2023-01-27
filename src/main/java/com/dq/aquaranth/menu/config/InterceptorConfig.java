package com.dq.aquaranth.menu.config;

import com.dq.aquaranth.menu.interceptor.AuthorizationInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 인터셉터 설정입니다.
 * 권한 체크를 우회할 수 있는 경로를 설정할 수 있습니다.
 * @author 김민준
 */
@Configuration
@RequiredArgsConstructor
public class InterceptorConfig implements WebMvcConfigurer{
    private final AuthorizationInterceptor authorizationInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authorizationInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/api/emp/registerLoginUser")
                .excludePathPatterns("/api/login/userinfo")
                .excludePathPatterns("/api/emp/loginlist")
                .excludePathPatterns("/api/token/refresh")
                .excludePathPatterns("/error/**")
                .excludePathPatterns("/swagger-resources/**")
                .excludePathPatterns("/swagger-ui.html/**")
                .excludePathPatterns("/webjars/**")
                .excludePathPatterns("/v3/api-docs")
        ;

    }
}
