package com.dev.careers.mapper;

import com.dev.careers.model.VotingItem;
import java.util.List;

public interface VotingItemMapper {
    List<VotingItem> getVotingItemList(int id);
}
