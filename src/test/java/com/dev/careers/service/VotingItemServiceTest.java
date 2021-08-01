package com.dev.careers.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.verify;

import com.dev.careers.mapper.VotingItemMapper;
import com.dev.careers.model.VotingItem;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class VotingItemServiceTest {

    @InjectMocks
    VotingItemService votingItemService;

    @Mock
    VotingItemMapper votingItemMapper;

    @BeforeEach
    public void setUp() throws Exception{
        MockitoAnnotations.openMocks(this);
        votingItemService = new VotingItemService(this.votingItemMapper);
        mockVotingItemMapper();
    }

    @AfterEach
    public void close() throws Exception{
        MockitoAnnotations.openMocks(this).close();
    }

    public void mockVotingItemMapper() {
        VotingItem votingItem = VotingItem.builder()
            .votingItemId(1)
            .votingId(1)
            .votingItemName("testItem")
            .voteCount(1)
            .build();

        willDoNothing().given(votingItemMapper).updateVotingItemCount(votingItem);
    }

    @Test
    @DisplayName("정상적인 투표 아이템 개수 증가 처리")
    public void countUpdate_ValidData_True() throws Exception{
        VotingItem votingItem = VotingItem.builder()
            .votingItemId(1)
            .votingId(1)
            .votingItemName("testItem")
            .voteCount(1)
            .build();

        votingItemService.countUpdate(votingItem);

        verify(votingItemMapper).updateVotingItemCount(any(VotingItem.class));
    }
}
