package com.dev.careers.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 투표 아이템 모델
 */
@Getter
@NoArgsConstructor
public class VotingItem {

    private int votingItemId;

    private int votingId;

    private String votingItemName;

    private int voteCount;
}
