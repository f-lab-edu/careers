package com.dev.careers.controller;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.dev.careers.domain.Curator;
import com.dev.careers.service.CuratorService;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@WebMvcTest(CuratorController.class)
public class CuratorControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    CuratorService curatorService;

    Curator curator;

    @Before
    public void setUp(){
        curator = new Curator(1L,"admin",
            "Abc12345@","admin@careers.com", "");
    }

    @Test
    public void create() throws Exception {
        mvc.perform(post("/curators").contentType(MediaType.APPLICATION_JSON)
            .content("{\"name\": \"admin\", \"password\": \"Abc12345@\", "
                + "\"email\": \"admin@careers.com\"}"))
            .andDo(print())
            .andExpect(status().isCreated());

    }

    @Test
    public void confirmEmail() throws Exception {
        String email = "admin@curators.com";

        mvc.perform(post("/curators/confirm-Email/admin@curators.com"))
            .andDo(print())
            .andExpect(content().string(containsString("false")));

        verify(curatorService).isDuplicateEmail(email);
    }

    @Test
    public void curatorLogin() throws Exception{

        mvc.perform(post("/curators/curator-login")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"email\":\"admin@careers.com\", \"password\":\"Abc12345@\"}"))
            .andDo(print())
            .andExpect(status().isOk());

        verify(curatorService).loginProcess(curator);
    }
}
