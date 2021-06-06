package com.dev.careers.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class VotingItem {

    private int votingItemId;

    private int votingId;

    private String votingItemName;

    private int voteCount;
}
