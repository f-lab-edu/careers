package com.dev.careers.service.session;

import java.util.Optional;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Component;

@Component
public class SessionAuthenticator {

    private final static String sessionName = "userID";
    private final HttpSession httpSession;

    public SessionAuthenticator(HttpSession httpSession) {
        this.httpSession = httpSession;
    }

    public void login(Integer id) {
        httpSession.setAttribute(sessionName,id);
    }

    public void logout() {
        httpSession.removeAttribute(sessionName);
    }

    public int successLoginUserId(){
        if (httpSession.getAttribute(sessionName) == null){
            return 0;
        }
        return (int)httpSession.getAttribute(sessionName);
    }
}
