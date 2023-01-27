package com.dq.aquaranth.menu.annotation;


import com.dq.aquaranth.menu.constant.MenuCodes;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 권한 체크용 어노테이션입니다.
 * 권한을 체크할 컨트롤러에 사용합니다.
 *
 * @author 김민준
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface MenuCode {
    MenuCodes value() default MenuCodes.ROOT;
}
