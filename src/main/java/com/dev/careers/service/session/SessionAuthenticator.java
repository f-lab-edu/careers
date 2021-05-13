package com.dev.careers.service.session;

import com.dev.careers.service.error.ViolationException;
import java.util.Optional;
import javax.servlet.http.HttpSession;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@EnableRedisHttpSession
public class SessionAuthenticator {

    public final static String SESSION_NAME = "userID";
    private final HttpSession httpSession;

    public SessionAuthenticator(HttpSession httpSession) {
        this.httpSession = httpSession;
    }

    public void login(int id) {
        httpSession.setAttribute(SESSION_NAME,id);
    }

    public void logout() {
        httpSession.removeAttribute(SESSION_NAME);
    }

    public int successLoginUserId(){
        Object userId = Optional.ofNullable(httpSession.getAttribute(SESSION_NAME))
            .orElseThrow(ViolationException::new);

        return (int) userId;
    }
}
