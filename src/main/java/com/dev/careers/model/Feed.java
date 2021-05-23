package com.dev.careers.model;

import java.sql.Timestamp;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * Feed Model
 */
@Setter
@Getter
public class Feed {

    public int feedId;

    @NotNull
    public String content;

    public String url;

    public Timestamp date;

    public int curatorId;

}
