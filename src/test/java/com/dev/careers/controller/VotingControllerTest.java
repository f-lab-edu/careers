package com.dev.careers.controller;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
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
    public void getVotings() throws Exception {
        List<Voting> votings = new ArrayList<>();
        LocalDateTime localDateTime = LocalDateTime.now();
        Timestamp timestamp = Timestamp.valueOf(localDateTime);
        votings.add(new Voting(1, "votingTest", 1, timestamp));

        given(votingService.getVotings(0)).willReturn(votings);

        mockMvc.perform(get("/curator/votings").param("cursor", "1"))
            .andDo(print())
            .andExpect(status().isOk());

    }

    @org.junit.jupiter.api.Test
    public void getVoting() throws Exception {
        LocalDateTime localDateTime = LocalDateTime.now();
        Timestamp timestamp = Timestamp.valueOf(localDateTime);
        List<VotingItem> votingItems = new ArrayList<>();
        votingItems.add(new VotingItem(1, 1, "item1", 1));
        votingItems.add(new VotingItem(2, 1, "item2", 2));
        Voting voting = new Voting(1, "Test1", 1,
            timestamp, timestamp, "voting_test", votingItems);

        given(votingService.getVoting(1)).willReturn(voting);

        mockMvc.perform(get("/curator/1/votings"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().string(containsString("\"votingId\":1")));
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

        mockMvc.perform(post("/curator/votings")
            .contentType(MediaType.APPLICATION_JSON)
            .content("\"votingId\":1, \"votingTitle\":\"test\", \"votingWriter\":1, "
                + "\"votingExplanation\":\"voting_create_test\", \"votingItems\":"
                + "[{ \"votingItemId\":1, \"votingId\":1, \"votingItemName\":\"item1\", \"voteCount\":1 },"
                + "{ \"votingItemId\":2, \"votingId\":1, \"votingItemName\":\"item2\", \"voteCount\":2 }]"))
            .andDo(print());

        verify(votingService).addVoting(voting);
    }


}
