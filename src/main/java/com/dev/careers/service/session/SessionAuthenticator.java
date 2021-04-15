package com.dev.careers.service.session;

import com.dev.careers.service.error.ViolationException;
import java.util.Optional;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Component;

@Component
public class SessionAuthenticator {

    private final static String SESSIONNAME = "userID";
    private final HttpSession httpSession;

    public SessionAuthenticator(HttpSession httpSession) {
        this.httpSession = httpSession;
    }

    public void login(Integer id) {
        httpSession.setAttribute(SESSIONNAME,id);
    }

    public void logout() {
        httpSession.removeAttribute(SESSIONNAME);
    }

    public int successLoginUserId(){
        Object userId = Optional.ofNullable(httpSession.getAttribute(SESSIONNAME))
            .orElseThrow(ViolationException::new);

        return (Integer) userId;
    }
}
