package com.dev.careers.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VotingItem {
    private int votingItemId;

    private int votingId;

    private String votingItemName;

    private int voteCount;
}
