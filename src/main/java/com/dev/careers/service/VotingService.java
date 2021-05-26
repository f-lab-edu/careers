package com.dev.careers.service;

import com.dev.careers.mapper.VotingMapper;
import com.dev.careers.model.Voting;
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

    public List<Voting> getVotings(){
        List<Voting> votings = votingMapper.getVotingList();
        return votings;
    }
}
