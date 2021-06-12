package com.dev.careers.service;

import com.dev.careers.mapper.VotingItemMapper;
import com.dev.careers.model.VotingItem;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


/**
 * 투표 아이템 관리 서비스
 */
@Service
@AllArgsConstructor
public class VotingItemService {

    private final VotingItemMapper votingItemMapper;

    /**
     * 투표 아이템 투표 갯수 변경
     *
     * @param votingItem 갯수 증가 시킬 투표 아이템 객체
     */
    public void countUpdate(VotingItem votingItem) {
        votingItemMapper.updateVotingItemCount(votingItem);
    }
}
