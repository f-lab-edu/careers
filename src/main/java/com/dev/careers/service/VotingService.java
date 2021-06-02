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
 * @author Byeong-jun
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

        List<VotingItem> votingItems = votingItemMapper.getVotingItemList(id);
        voting.setVotingItems(votingItems);
        return voting;
    }

    public void addVoting(Voting voting) {
        votingMapper.saveVoting(voting);
        votingItemMapper.saveVotingMapper(voting.getVotingItems());
    }

    public void deleteVoting(int id) {
        votingMapper.removeVoting(id);
        votingItemMapper.removeVotingItem(id);
    }
}
