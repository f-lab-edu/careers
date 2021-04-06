package com.dev.careers.service.session;

import com.dev.careers.model.LoginParamter;
import java.util.Optional;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
}
