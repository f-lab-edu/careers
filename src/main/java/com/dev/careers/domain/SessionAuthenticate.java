package com.dev.careers.domain;

import javax.servlet.http.HttpSession;

public class SessionAuthenticate {

    public final static String SESSIONNAME = "sessionInfo";
    private final HttpSession httpSession;

    public SessionAuthenticate(HttpSession httpSession) {
        this.httpSession = httpSession;
    }

    public Curator getHttpSession(String sessionName){
        return (Curator)httpSession.getAttribute(sessionName);
    }

    public void setHttpSession(Curator curator){
        httpSession.setAttribute(SESSIONNAME, curator);
    }

    public void httpSessionRemove(String sessionName){
        httpSession.removeAttribute(sessionName);
    }

}