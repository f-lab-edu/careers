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
    private int id;

    @NotEmpty
    private String title;

    @NotEmpty
    private String writer;

    private Date timestamp;

    @NotEmpty
    private String explanation;

    private List<VotingItem> votingItem;

    public void setVotingItem(List<VotingItem> votingItem){
        this.votingItem = new ArrayList<>(votingItem);
    }
}
