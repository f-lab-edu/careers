package com.dev.careers.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class CuratorControllerTest {

    @Autowired
    CuratorController curatorController;
    MockMvc mockMvc;

    @BeforeEach
    public void beforeEach(){
        mockMvc = MockMvcBuilders.standaloneSetup(curatorController).build();
    }

    @Test
    @DisplayName("정상적인 회원가입")
    public void joinCurator() throws Exception {
        mockMvc.perform(post("/curators/join")
        .param("email","test@google.com")
        .param("name", "홍길동")
        .param("password", "test123!@"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("잘못된 이메일 형식 요청")
    public void violationEmail() throws Exception {
        mockMvc.perform(post("/curators/join")
        .param("email", "test123.com")
        .param("name", "홍길동")
        .param("password", "test123!@"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("Email Format Violation"));
    }

    @Test
    @DisplayName("잘못된 비밀번호 형식 요청")
    public void violationPassword() throws Exception {
        mockMvc.perform(post("/curators/join")
                .param("email", "test@google.com")
                .param("name", "홍길동")
                .param("password", "123"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("Password Format Violation"));
    }
}