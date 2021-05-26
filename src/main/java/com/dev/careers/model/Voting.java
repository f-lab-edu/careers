package com.dev.careers.model;

import java.util.Date;
import java.util.List;
import javax.validation.constraints.NotEmpty;
import lombok.Getter;
import org.springframework.lang.Nullable;

@Getter
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
}
