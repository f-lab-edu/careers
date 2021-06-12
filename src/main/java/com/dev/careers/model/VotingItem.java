package com.dev.careers.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 투표 아이템 모델
 */
@Getter
@Setter
@NoArgsConstructor
public class VotingItem {

    private int votingItemId;

    private int votingId;

    private String votingItemName;

    private int voteCount;
}
