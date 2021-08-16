package com.dev.careers.controller;

import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.dev.careers.model.Curator;
import com.dev.careers.model.LoginParamter;
import com.dev.careers.service.CuratorService;
import com.dev.careers.service.error.CareersExceptionHandler;
import com.dev.careers.service.session.SessionAuthenticator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CuratorController.class)
class CuratorControllerTest {

    MockMvc mockMvc;

    @MockBean
    CuratorService curatorService;

    @MockBean
    SessionAuthenticator sessionAuthenticator;

    MockHttpSession mockHttpSession;

    @BeforeEach
    public void beforeEach() {
        mockMvc = MockMvcBuilders.standaloneSetup(
            new CuratorController(this.curatorService, this.sessionAuthenticator))
            .setControllerAdvice(new CareersExceptionHandler()).build();
    }

    @Test
    @DisplayName("정상적인 회원가입")
    public void joinCurator() throws Exception {
        Curator curator = new Curator(
            "test@google.com", "홍길동", "test123!@");

        willDoNothing().given(curatorService).join(curator);
        curatorService.join(curator);

        mockMvc.perform(post("/curators/join")
            .param("email", "test@google.com")
            .param("name", "홍길동")
            .param("password", "test123!@"))
            .andDo(print())
            .andExpect(status().isOk());

        verify(curatorService, times(1)).join(curator);
    }

    @Test
    @DisplayName("잘못된 이메일 형식 요청")
    public void violationEmail() throws Exception {
        mockMvc.perform(post("/curators/join")
            .param("email", "test123.com")
            .param("name", "홍길동")
            .param("password", "test123!@"))
            .andDo(print())
            .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("잘못된 비밀번호 형식 요청")
    public void violationPassword() throws Exception {
        mockMvc.perform(post("/curators/join")
            .param("email", "test@google.com")
            .param("name", "홍길동")
            .param("password", "123"))
            .andDo(print())
            .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("로그인 시 세션발급 확인")
    public void checkLoginSession() throws Exception {
        LoginParamter loginParamter = new LoginParamter("aaa@google.com",
            "test123!@");
        mockHttpSession = new MockHttpSession();
        mockHttpSession.setAttribute("userID", 1);


        given(curatorService.getUserIdByEmailAndPassword(loginParamter)).willReturn(1);
        int id = curatorService.getUserIdByEmailAndPassword(loginParamter);

        willDoNothing().given(sessionAuthenticator).login(id);
        sessionAuthenticator.login(id);

        mockMvc.perform(post("/curators/login")
            .param("email", "aaa@google.com")
            .param("password", "test123!@")
            .session(mockHttpSession))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(request().sessionAttribute(
                SessionAuthenticator.SESSION_NAME, notNullValue()));

        verify(curatorService, times(1)).getUserIdByEmailAndPassword(loginParamter);
        verify(sessionAuthenticator, times(1)).login(id);
    }
}