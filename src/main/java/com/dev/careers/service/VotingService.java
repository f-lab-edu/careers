package com.dev.careers.service;

import com.dev.careers.mapper.VotingItemMapper;
import com.dev.careers.mapper.VotingMapper;
import com.dev.careers.model.Voting;
import com.dev.careers.model.VotingItem;
import com.dev.careers.service.error.AuthorMismatchException;
import com.dev.careers.service.error.FailToSaveVotingException;
import com.dev.careers.service.error.VotingNotFoundException;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


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


    @Transactional
    public List<Voting> getVotings(){
        List<Voting> votings = votingMapper.getVotingList();
        return votings;
    }

    @Transactional
    public Voting getVoting(int id) {
        Voting voting = votingMapper.getVoting(id).orElseThrow(
            ()-> new VotingNotFoundException(id));
            List<VotingItem> votingItems = votingItemMapper.getVotingItemList(id);
            voting.setVotingItems(votingItems);
        return voting;
    }

    @Transactional
    public void addVoting(Voting voting) {
        try {
            votingMapper.saveVoting(voting);
            votingItemMapper.saveVotingMapper(voting.getVotingItems());
        }catch (Exception exception) {
            throw new FailToSaveVotingException("투표를 저장할 수 없습니다.");
        }
    }

    @Transactional
    public void deleteVoting(int votingId, int votingWriter) {
        if(votingWriter == votingMapper.getVotingWriter(votingId)) {
            votingMapper.removeVoting(votingId);
            votingItemMapper.removeVotingItem(votingId);
        }else{
            throw new AuthorMismatchException("해당 요청자와 투표 작성자는 일치하지 않습니다.");
        }
    }
}
