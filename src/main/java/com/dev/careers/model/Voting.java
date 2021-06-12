package com.dev.careers.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

/**
 * 투표 정보 모델
 */
@Getter
@Setter
public class Voting {

    /**
     * 투표 저장 요청으로 받은 투표 객체에 작성 시간, 생성 시간 추가 생성자
     *
     * @param voting 저장 요청 투표 객체
     * @param timestamp 투표 작성 시간
     * @param deadline 투표 마감 시간
     */
    public Voting(Voting voting, Timestamp timestamp, Timestamp deadline) {
        this.votingTitle = voting.getVotingTitle();
        this.votingWriter = voting.getVotingWriter();
        this.votingExplanation = voting.getVotingExplanation();
        this.votingItems = voting.getVotingItems();
        this.timestamp = timestamp;
        this.deadline = deadline;
    }

    private int votingId;

    @NotEmpty
    private String votingTitle;

    @NotEmpty
    private int votingWriter;

    private Timestamp timestamp;

    private Timestamp deadline;

    @NotEmpty
    private String votingExplanation;

    private List<VotingItem> votingItems;

    public void setVotingItems(List<VotingItem> votingItem) {
        this.votingItems = new ArrayList<>(votingItem);
    }
}
