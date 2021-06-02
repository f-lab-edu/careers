package com.dev.careers.aop;

import com.dev.careers.service.session.SessionAuthenticator;
import lombok.AllArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

/**
 * Aspect 애노테이션을 사용하여  AOP를 처리한다. Before 애노테이션을 사용하여 포인트 컷에 위치한 애노테이션을 사용하는 모든 메서드가 호출되기 전에
 * loginCheck 메서드가 호출되어 로그인 체크 유무를 확인하도록 한다.
 *
 * @author junehee
 */
@Aspect
@Component
@AllArgsConstructor
public class LoginCheckAop {

    private final SessionAuthenticator sessionAuthenticator;

    @Before("@annotation(com.dev.careers.annotation.LoginChecker)")
    public void loginCheck() {
        sessionAuthenticator.successLoginUserId();
    }
}
