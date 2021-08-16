package com.dev.careers.service;

import com.dev.careers.mapper.VotingItemMapper;
import com.dev.careers.model.VotingItem;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * 투표 아이템 관리 서비스
 *
 * @author Byeong-jun
 */
@Service
@AllArgsConstructor
public class VotingItemService {

    private final VotingItemMapper votingItemMapper;

    /**
     * 투표 아이템 투표 개수 변경
     *
     * @param votingItem 갯수 증가 시킬 투표 아이템 객체
     */
    @Transactional
    public void countUpdate(VotingItem votingItem) {
        votingItemMapper.updateVotingItemCount(votingItem);
    }
}
