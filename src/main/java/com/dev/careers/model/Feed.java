package com.dev.careers.model;

import java.sql.Timestamp;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Feed Model
 */
@Getter
@AllArgsConstructor
public class Feed {

    public int feedId;

    @NotNull(message = "컨텐츠 내용을 입력해주세요")
    public String content;

    public String url;

    public Timestamp date;

    public int curatorId;

}
