package com.dev.careers.controller;

import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.dev.careers.model.VotingItem;
import com.dev.careers.service.VotingItemService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@WebMvcTest(VotingItemController.class)
public class VotingItemControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    VotingItemService votingItemService;

    @BeforeEach
    public void setUp(WebApplicationContext webApplicationContext,
        RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
            .apply(documentationConfiguration(restDocumentation))
            .build();
    }

    @Test
    @DisplayName("정상적인 투표 아이템 개수 증가 요청")
    public void countUpdate_ValidData_True() throws Exception {
        VotingItem votingItem = VotingItem.builder()
            .votingItemId(1)
            .votingId(1)
            .votingItemName("testItem")
            .voteCount(1)
            .build();

        willDoNothing().given(votingItemService).countUpdate(isA(VotingItem.class));
        votingItemService.countUpdate(votingItem);

        mockMvc.perform(post("/curator/votings/voting-items")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{ \"votingItemId\":1, "
                + "\"votingId\":1, "
                + "\"votingItemName\":\"test1\", "
                + "\"voteCount\":1 }"))
            .andDo(document("votings"))
            .andExpect(status().isOk());

        verify(votingItemService, times(1)).countUpdate(votingItem);
    }

    @Test
    @DisplayName("잘못된 형식 데이터 투표 아이템 개수 증가 요청")
    public void countUpdate_InValidData_ExceptionThrown() throws Exception {
        mockMvc.perform(post("/curator/votings/voting-items")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{ \"votingItemId\":1, "
                + "\"votingId\":1, "
                + "\"votingItemName\":\"test1\", "
                + "\"voteCount\":\"error\"}"))
            .andDo(print())
            .andExpect(status().isBadRequest());
    }
}
