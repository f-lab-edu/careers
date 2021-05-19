package com.dev.careers.service.session;

import com.dev.careers.service.error.ViolationException;
import java.util.Optional;
import javax.servlet.http.HttpSession;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * Set-Cookies에 Session에 대한 정보를 설정 및 제거하기위한 클래스
 */
@EnableRedisHttpSession
public class SessionAuthenticator {

    public static final String SESSION_NAME = "userID";
    private final HttpSession httpSession;

    public SessionAuthenticator(HttpSession httpSession) {
        this.httpSession = httpSession;
    }

    public void login(int id) {
        httpSession.setAttribute(SESSION_NAME, id);
    }

    public void logout() {
        httpSession.removeAttribute(SESSION_NAME);
    }

    /**
     * 요청헤더에 SESSION_NAME의 value값을 가져와 반환한다.
     * value가 없으면 예외를 발생시킨다.
     *
     * @return 큐레이터 아이디
     */
    public int successLoginUserId() {
        Object userId = Optional.ofNullable(httpSession.getAttribute(SESSION_NAME))
            .orElseThrow(ViolationException::new);

        return (int) userId;
    }
}
