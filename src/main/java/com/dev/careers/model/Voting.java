package com.dev.careers.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;

@Getter
@Setter
public class Voting {

    @Nullable
    private int votingId;

    @NotEmpty
    private String votingTitle;

    @NotEmpty
    private int votingWriter;

    private Date timestamp;

    @NotEmpty
    private String votingExplanation;

    private List<VotingItem> votingItems;

    public void setVotingItems(List<VotingItem> votingItem){
        this.votingItems = new ArrayList<>(votingItem);
    }
}
