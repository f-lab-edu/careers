package com.dev.careers.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Login 이후 요청사항에 로그인 유무 체크를 하기위해 만든 annotation 메서드에만 설정할 것 임으로 Target은 Method로 설정
 * RetentionPolicy.RUNTIME은 애노테이션을 런타임 시간에도 사용할 수 있게하여 애노테이션을 찾을 수 있도록 한다.
 *
 * @author junehee
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface LoginChecker {

}
