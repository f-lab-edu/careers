package com.dev.careers.mapper;

import com.dev.careers.model.VotingItem;
import java.util.List;

public interface VotingItemMapper {

    List<VotingItem> getVotingItemList(int votingId);

    VotingItem getVotingItem(VotingItem votingItem);

    void saveVotingMapper(List<VotingItem> votingItems);

    void updateVotingItemCount(VotingItem votingItem);

    void removeVotingItem(int votingId);
}
