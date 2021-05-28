package com.dev.careers.service;

import com.dev.careers.mapper.VotingItemMapper;
import com.dev.careers.mapper.VotingMapper;
import com.dev.careers.model.Voting;
import com.dev.careers.model.VotingItem;
import com.dev.careers.service.error.VotingNotFoundException;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


/**
 * 투표 관리 서비스
 *
 * @author byeongjun
 */
@AllArgsConstructor
@Service
public class VotingService {

    private final VotingMapper votingMapper;

    private final VotingItemMapper votingItemMapper;

    public List<Voting> getVotings(){
        List<Voting> votings = votingMapper.getVotingList();
        return votings;
    }

    public Voting getVoting(int id) {
        Voting voting = votingMapper.getVoting(id).orElseThrow(
            ()-> new VotingNotFoundException(id));

        List<VotingItem> votingItem = votingItemMapper.getVotingItemList(id);
        voting.setVotingItem(votingItem);
        return voting;
    }
}
