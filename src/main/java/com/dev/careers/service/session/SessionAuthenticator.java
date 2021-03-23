package com.dev.careers.service.session;

import com.dev.careers.model.LoginParamter;
import java.util.Optional;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Component;

@Component
public class SessionAuthenticator {

    private final static String sessionName = "sessionInfo";


    public void setSession(Optional<HttpSession> httpSession, LoginParamter loginParamter) {
        httpSession.ifPresent(session -> session.setAttribute(sessionName, loginParamter));
    }

    public void deleteSession(Optional<HttpSession> httpSession) {
        httpSession.ifPresent(session -> session.removeAttribute(sessionName));
    }
}
