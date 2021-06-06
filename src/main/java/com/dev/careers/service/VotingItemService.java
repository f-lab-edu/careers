package com.dev.careers.service;

import com.dev.careers.mapper.VotingItemMapper;
import com.dev.careers.model.VotingItem;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class VotingItemService {

    private VotingItemMapper votingItemMapper;

    public void countUpdate(int votingId, String votingItemName){
        VotingItem votingItem = VotingItem.builder()
            .votingId(votingId).votingItemName(votingItemName).build();

        VotingItem importedVotingItem = votingItemMapper.getVotingItem(votingItem);

        votingItem = VotingItem.builder()
            .votingItemId(importedVotingItem.getVotingItemId())
            .voteCount(importedVotingItem.getVoteCount() + 1).build();

        votingItemMapper.updateVotingItemCount(votingItem);
    }
}
