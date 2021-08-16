package com.dev.careers.controller;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.dev.careers.model.Voting;
import com.dev.careers.model.VotingItem;
import com.dev.careers.service.VotingService;
import com.dev.careers.service.error.CursorOutOfRangeException;
import com.dev.careers.service.session.SessionAuthenticator;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 * 투표 관리 컨트롤러 테스트
 *
 * @author Byeong-jun
 */

@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@WebMvcTest(VotingController.class)
public class VotingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VotingService votingService;

    @MockBean
    private SessionAuthenticator sessionAuthenticator;

    @BeforeEach
    @DisplayName("Spring Rest Docs 문서화 서비스 요청 설정")
    public void setUp(WebApplicationContext webApplicationContext,
        RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
            .apply(documentationConfiguration(restDocumentation))
            .build();
    }

    @Test
    @DisplayName("정상적인 투표 목록 조회 요청")
    public void getVotings_ValidData_True() throws Exception {
        List<Voting> votings = new ArrayList<>();
        LocalDateTime localDateTime = LocalDateTime.now();
        Timestamp timestamp = Timestamp.valueOf(localDateTime);
        votings.add(Voting.builder()
            .votingId(1)
            .votingTitle("Test")
            .votingWriter(1)
            .timestamp(timestamp)
            .build());

        given(votingService.getVotings(0)).willReturn(votings);

        mockMvc.perform(get("/curator/votings?cursor=0"))
            .andExpect(status().isOk())
            .andDo(print())
            .andDo(document("/votings/list", requestParameters(
                parameterWithName("cursor").description("페이징 처리된 투표 리스트 현재 위치 ID")
            ), responseFields(
                fieldWithPath("[].votingId").description("투표 아이디"),
                fieldWithPath("[].votingTitle").description("투표 제목 "),
                fieldWithPath("[].votingWriter").description("투표 작성자"),
                fieldWithPath("[].timestamp").description("투표 작성 날짜"),
                fieldWithPath("[].deadline").description("투표 마감 날짜").optional(),
                fieldWithPath("[].votingExplanation").description("투표 설명").optional(),
                fieldWithPath("[].votingItems").description("투표 아이템 목록").optional()
            )));


        verify(votingService, times(1)).getVotings(0);
    }

    @Test
    @DisplayName("cursor가 현재 저장된 투표수보다 높은 숫자인 경우 투표 목록 조회 요청")
    public void getVotings_CursorOutOfRange_ExceptionThrown() throws Exception {
        given(votingService.getVotings(10))
            .willThrow(new CursorOutOfRangeException("투표 목록 조회 범위를 초과하였습니다."));

        mockMvc.perform(get("/curator/votings?cursor=10"))
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(content().string("투표 목록 조회 범위를 초과하였습니다."));
    }

    @Test
    @DisplayName("정상적인 투표 상세 조회 요청")
    public void detail_ValidData_True() throws Exception {
        LocalDateTime localDateTime = LocalDateTime.now();
        Timestamp timestamp = Timestamp.valueOf(localDateTime);
        Timestamp deadline = Timestamp.valueOf(localDateTime.plusDays(7));
        List<VotingItem> votingItems = new ArrayList<>();

        votingItems.add(VotingItem.builder()
            .votingItemId(1)
            .votingId(1)
            .votingItemName("testItem1")
            .voteCount(1)
            .build());
        votingItems.add(VotingItem.builder()
            .votingItemId(2)
            .votingId(1)
            .votingItemName("testItem2")
            .voteCount(2)
            .build());

        Voting voting = Voting.builder()
            .votingId(1)
            .votingTitle("Test")
            .votingWriter(1)
            .timestamp(timestamp)
            .deadline(deadline)
            .votingExplanation("getVoting_Test")
            .votingItems(votingItems)
            .build();

        given(votingService.getVoting(1)).willReturn(voting);

        mockMvc.perform(RestDocumentationRequestBuilders
            .get("/curator/{votingId}/votings", 1))
            .andExpect(status().isOk())
            .andExpect(content().string(containsString("\"votingId\":1")))
            .andExpect(content().string(containsString("\"votingItemId\":1")))
            .andDo(print())
            .andDo(document("/votings/view", pathParameters(
                parameterWithName("votingId").description("상세 조회 투표 아이디")
            ), responseFields(
                fieldWithPath("votingId").description("상세 조회 투표 아이디"),
                fieldWithPath("votingTitle").description("투표 제목"),
                fieldWithPath("votingWriter").description("투표 작성자"),
                fieldWithPath("timestamp").description("투표 작성 날짜, 시간"),
                fieldWithPath("deadline").description("투표 종료 날짜, 시간"),
                fieldWithPath("votingExplanation").description("투표 설명"),
                fieldWithPath("votingItems[].votingItemId").description("투표 아이템 아이디"),
                fieldWithPath("votingItems[].votingId").description("투표 아이템 해당 투표 아이디"),
                fieldWithPath("votingItems[].votingItemName").description("투표 아이템 이름"),
                fieldWithPath("votingItems[].voteCount").description("투표 아이템 투표수")
            )));

        verify(votingService, times(1)).getVoting(1);
    }

    @Test
    @DisplayName("잘못된 형식 데이터 투표 상세 조회 요청")
    public void detail_InvalidData_ExceptionThrown() throws Exception {
        mockMvc.perform(get("/curator/a/votings"))
            .andDo(print())
            .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("정상적인 데이터 형식 투표 생성 요청")
    public void create_ValidData_True() throws Exception {
        List<VotingItem> votingItems = new ArrayList<>();
        votingItems.add(new VotingItem(1, 1, "item1", 1));
        votingItems.add(new VotingItem(2, 1, "item2", 2));
        Voting voting = Voting.builder()
            .votingId(1)
            .votingTitle("test")
            .votingWriter(1)
            .votingExplanation("voting_create_test")
            .votingItems(votingItems)
            .build();

        willDoNothing().given(votingService).addVoting(isA(Voting.class));
        votingService.addVoting(voting);

        mockMvc.perform(post("/curator/votings")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{ \"votingId\":1, \"votingTitle\":\"test\", \"votingWriter\":1,"
                + " \"votingExplanation\":\"voting_create_test\", \"votingItems\":"
                + "[{ \"votingItemId\":1, \"votingId\":1, "
                + "\"votingItemName\":\"item1\", \"voteCount\":1 }, "
                + "{ \"votingItemId\":2, \"votingId\":1, "
                + "\"votingItemName\":\"item2\", \"voteCount\":2 }] }"))
            .andExpect(status().isOk())
            .andDo(print())
            .andDo(document("/votings/create", requestFields(
                fieldWithPath("votingId").description("생성 투표 아이디"),
                fieldWithPath("votingTitle").description("투표 제목"),
                fieldWithPath("votingWriter").description("투표 작성자"),
                fieldWithPath("votingExplanation").description("투표 설명"),
                fieldWithPath("votingItems.[].votingItemId").description("투표 아이템 아이디"),
                fieldWithPath("votingItems.[].votingId").description("해당 투표 아이템을 가지고 있는 투표 아이디"),
                fieldWithPath("votingItems.[].votingItemName").description("투표 아이템 이름"),
                fieldWithPath("votingItems.[].voteCount").description("투표 아이템 현재 투표수")
            )));

        verify(votingService, times(1)).addVoting(voting);
    }

    @Test
    @DisplayName("잘못된 형식 데이터 투표 생성 요청")
    public void create_InvalidData_ExceptionThrown() throws Exception {
        mockMvc.perform(post("/curator/votings").contentType(MediaType.APPLICATION_JSON)
            .content("{ \"votingId\":1, \"votingTitle\":, \"votingWriter\":1, "
                + "\"votingExplanation\":\"voting_create_test\", \"votingItems\":"
                + "[{ \"votingItemId\":1, \"votingId\":1, "
                + "\"votingItemName\":\"item1\", \"voteCount\":1 },"
                + "{ \"votingItemId\":2, \"votingId\":1, "
                + "\"votingItemName\":\"item2\", \"voteCount\":2 }] }"))
            .andDo(print())
            .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("정상적인 투표 제거 요청")
    public void votingDelete_ValidData_True() throws Exception {
        int votingId = 1;
        int votingWriter = 1;

        given(sessionAuthenticator.successLoginUserId()).willReturn(votingWriter);
        willDoNothing().given(votingService).deleteVoting(isA(Integer.class), isA(Integer.class));

        mockMvc.perform(RestDocumentationRequestBuilders
            .delete("/curator/{votingId}/votings/", votingId))
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("/votings/delete", pathParameters(
                parameterWithName("votingId").description("삭제 투표 아이디")
            )));

        verify(sessionAuthenticator, times(1)).successLoginUserId();
        verify(votingService, times(1)).deleteVoting(votingId, votingWriter);
    }

    @Test
    @DisplayName("잘못된 형식 데이터 투표 제거 요청")
    public void votingDelete_InvalidData_ExceptionThrown() throws Exception {
        mockMvc.perform(delete("/curator/a/votings/"))
            .andDo(print())
            .andExpect(status().isBadRequest());
    }


}
