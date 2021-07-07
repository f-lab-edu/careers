package com.dev.careers.controller;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.dev.careers.model.Voting;
import com.dev.careers.model.VotingItem;
import com.dev.careers.service.VotingService;
import com.dev.careers.service.session.SessionAuthenticator;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@WebMvcTest(VotingController.class)
public class VotingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VotingService votingService;

    @MockBean
    private SessionAuthenticator sessionAuthenticator;

    @org.junit.jupiter.api.Test
    @DisplayName("정상적인 투표 목록 조회")
    public void getVotings() throws Exception {
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

        mockMvc.perform(get("/curator/votings").param("cursor", "1"))
            .andDo(print())
            .andExpect(status().isOk());

        verify(votingService, times(1)).getVotings(0);
    }

    @org.junit.jupiter.api.Test
    public void getVoting() throws Exception {
        LocalDateTime localDateTime = LocalDateTime.now();
        Timestamp timestamp = Timestamp.valueOf(localDateTime);
        Timestamp deadline = Timestamp.valueOf(localDateTime.plusDays(7));
        List<VotingItem> votingItems = new ArrayList<>();
        votingItems.add(new VotingItem(1, 1, "item1", 1));
        votingItems.add(new VotingItem(2, 1, "item2", 2));
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

        mockMvc.perform(get("/curator/1/votings"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().string(containsString("\"votingId\":1")))
            .andExpect(content().string(containsString("\"votingItemId\":1")));

        verify(votingService, times(1)).getVoting(1);
    }

    @org.junit.jupiter.api.Test
    public void create() throws Exception {
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
            .content("\"votingId\":1, \"votingTitle\":\"test\", \"votingWriter\":1, "
                + "\"votingExplanation\":\"voting_create_test\", \"votingItems\":"
                + "[{ \"votingItemId\":1, \"votingId\":1, \"votingItemName\":\"item1\", \"voteCount\":1 },"
                + "{ \"votingItemId\":2, \"votingId\":1, \"votingItemName\":\"item2\", \"voteCount\":2 }]"))
            .andDo(print());

        verify(votingService, times(1)).addVoting(voting);
    }

    @Test
    public void votingDelete() throws Exception {
        int votingId = 1;
        int votingWriter = 1;

        given(sessionAuthenticator.successLoginUserId()).willReturn(votingWriter);
        willDoNothing().given(votingService).deleteVoting(isA(Integer.class), isA(Integer.class));

        mockMvc.perform(delete("/curator/1/votings/"))
            .andDo(print())
            .andExpect(status().isOk());

        verify(sessionAuthenticator, times(1)).successLoginUserId();
        verify(votingService, times(1)).deleteVoting(votingId, votingWriter);
    }


}
