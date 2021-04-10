package com.dev.careers.controller;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.dev.careers.domain.Curator;
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
    public void create() throws Exception {
        Curator curator = new Curator(1L,"admin",
            "Abc12345@","admin@curators.com");

        mvc.perform(post("/curators").contentType(MediaType.APPLICATION_JSON)
            .content("{\"name\": \"admin\", \"password\": \"Abc12345@\", "
                + "\"email\": \"admin@curators.com\"}"))
            .andDo(print())
            .andExpect(status().isCreated());

        verify(curatorService).addCurator(refEq(curator));
    }

    @Test
    public void confirmEmail() throws Exception {
        String email = "admin@curators.com";

        mvc.perform(post("/curators/confirmEmail/admin@curators.com"))
            .andDo(print())
            .andExpect(content().string(containsString("")));

        verify(curatorService).isDuplicateEmail(eq(email));

    }
}
