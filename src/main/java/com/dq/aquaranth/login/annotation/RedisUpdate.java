package com.dq.aquaranth.login.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 레디스의 상태를 업데이트 시켜주는 커스텀 어노테이션 입니다.
 *
 * @author jonghyun
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RedisUpdate {
}
