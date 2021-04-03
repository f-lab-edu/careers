package com.dev.careers.controller;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.dev.careers.service.CuratorService;
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

    @Test
    public void list() throws Exception {
        mvc.perform(get("/curators"))
            .andDo(print())
            .andExpect(status().isOk());
    }

    @Test
    public void create() throws Exception {
        mvc.perform(post("/curators").contentType(MediaType.APPLICATION_JSON)
            .content("{\"id\":1, \"name\": \"admin\", \"password\": \"Abc12345@\", \"email\": \"admin@curators.com\"}"))
            .andDo(print())
            .andExpect(status().isCreated());
    }

    @Test
    public void confirmEmail() throws Exception {
        String email = "Abc12345@";

        mvc.perform(post("/curators/Abc123456@"))
            .andDo(print())
            .andExpect(content().string(containsString("0")));

    }
}
