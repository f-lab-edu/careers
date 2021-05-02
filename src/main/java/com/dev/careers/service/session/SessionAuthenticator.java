package com.dev.careers.service.session;

import com.dev.careers.model.LoginParamter;
import java.util.Optional;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SessionAuthenticator {

    private final static String sessionName = "sessionInfo";
    private final HttpSession httpSession;

    public SessionAuthenticator(HttpSession httpSession) {
        this.httpSession = httpSession;
    }

    public void login(LoginParamter loginParamter) {
        httpSession.setAttribute(sessionName,loginParamter);
    }

    public void logout() {
        httpSession.removeAttribute(sessionName);
    }
}
