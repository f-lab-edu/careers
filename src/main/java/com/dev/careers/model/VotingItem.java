package com.dev.careers.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 투표 아이템 모델
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class VotingItem {

    private int votingItemId;

    private int votingId;

    private String votingItemName;

    private int voteCount;
}
