package com.dev.careers.mapper;

import com.dev.careers.model.VotingItem;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

/**
 * 투표 아이템 Mapper
 */
@Mapper
public interface VotingItemMapper {

    List<VotingItem> getVotingItemList(int votingId);

    void saveVotingItems(List<VotingItem> votingItems);

    void updateVotingItemCount(VotingItem votingItem);

    void removeVotingItems(int votingId);
}
