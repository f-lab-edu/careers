package com.dev.careers.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyCollection;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.verify;

import com.dev.careers.mapper.VotingItemMapper;
import com.dev.careers.mapper.VotingMapper;
import com.dev.careers.model.Voting;
import com.dev.careers.model.VotingItem;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class VotingServiceTest {

    @Mock
    VotingMapper votingMapper;

    @Mock
    VotingItemMapper votingItemMapper;

    VotingService votingService;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
        mockVotingMapper();
        mockVotingItemMapper();
        votingService = new VotingService(this.votingMapper, this.votingItemMapper,1);
    }

    @AfterEach
    public void close() throws Exception {
        MockitoAnnotations.openMocks(this).close();
    }

    public void mockVotingMapper() {
        List<Voting> votings = new ArrayList<Voting>();
        LocalDateTime localDateTime = LocalDateTime.now();
        Timestamp timestamp = Timestamp.valueOf(localDateTime);
        Timestamp deadline = Timestamp.valueOf(localDateTime.plusDays(7));
        Voting voting = Voting.builder()
            .votingId(1)
            .votingTitle("Test")
            .votingWriter(1)
            .timestamp(timestamp)
            .deadline(deadline)
            .votingExplanation("getVoting_Test")
            .build();
        votings.add(voting);

        given(votingMapper.getTotalVotingCount()).willReturn(1);
        given(votingMapper.getVotingList(1, 1)).willReturn(votings);

        given(votingMapper.getVoting(1)).willReturn(Optional.of(voting));

        willDoNothing().given(votingMapper).saveVoting(any(Voting.class));

        given(votingMapper.getVotingWriter(1)).willReturn(1);
        willDoNothing().given(votingMapper).removeVoting(anyInt());

    }

    public void mockVotingItemMapper() {
        List<VotingItem> votingItems = new ArrayList<VotingItem>();
        VotingItem votingItem = VotingItem.builder()
            .votingItemId(1)
            .votingId(1)
            .votingItemName("testItem")
            .voteCount(1)
            .build();
        votingItems.add(votingItem);

        given(votingItemMapper.getVotingItemList(1)).willReturn(votingItems);

        willDoNothing().given(votingItemMapper).saveVotingItems(anyList());

        willDoNothing().given(votingItemMapper).removeVotingItems(anyInt());

    }

    @Test
    public void getVotings_ValidData_True() throws Exception {
        List<Voting> votings = votingService.getVotings(1);
        Voting voting = votings.get(0);

        assertThat(voting.getVotingId(), is(1));

    }

    @Test
    public void getVoting_ValidData_True() throws Exception {
        Voting voting = votingService.getVoting(1);

        verify(votingItemMapper).getVotingItemList(eq(1));

        assertThat(voting.getVotingId(), is(1));

        VotingItem votingItem = voting.getVotingItems().get(0);

        assertThat(votingItem.getVotingItemName(), is("testItem"));
    }

    @Test
    public void addVoting_ValidData_True() {
        votingService.addVoting(any(Voting.class));

        verify(votingMapper).saveVoting(any(Voting.class));

        verify(votingItemMapper).saveVotingItems(anyList());

    }

    @Test
    public void deleteVoting_ValidData_True() {
        votingService.deleteVoting(1,1);

        assertThat(votingMapper.getVotingWriter(1), is(1));

        verify(votingMapper).removeVoting(anyInt());

        verify(votingItemMapper).removeVotingItems(anyInt());
    }

}
