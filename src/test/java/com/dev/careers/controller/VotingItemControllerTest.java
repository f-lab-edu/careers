package com.dev.careers.controller;

import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.request;
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

/**
 * 투표 아이템 관리 컨트롤러 테스트
 *
 * @author Byeong-jun
 */

@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@WebMvcTest(VotingItemController.class)
public class VotingItemControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    VotingItemService votingItemService;

    /**
     * Spring Rest Docs 문서화 서비스 요청 설정
     *
     * @param webApplicationContext 웹 애플리케이션에 대한 구성을 제공하는 인터페이스
     * @param restDocumentation Spring Rest docs 문서화 제공 인터페이스
     */
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
            .andExpect(status().isOk())
            .andDo(document("/votings/items_countupdate", requestFields(
                fieldWithPath("votingItemId").description("투표 아이템 아이디"),
                fieldWithPath("votingId").description("해당 투표 아이템을 가지고 있는 투표 아이디"),
                fieldWithPath("votingItemName").description("투표 아이템 이름"),
                fieldWithPath("voteCount").description("투표 아이템 현재 투표수")
            )));

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
