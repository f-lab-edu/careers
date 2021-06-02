package com.dev.careers.mapper;

import com.dev.careers.model.VotingItem;
import java.util.List;

public interface VotingItemMapper {
    List<VotingItem> getVotingItemList(int votingId);

    void saveVotingMapper(List<VotingItem> votingItems);

    void removeVotingItem(int votingId);
}
