package com.dev.careers.model;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.lang.Nullable;

/**
 * Comment Model
 * Setter 애노테이션은 댓글 추가 시 DB에서 auto_increment 한 CommentId 값을 주입받기 위해 사용하였습니다.
 */
@Setter
@Getter
public class Comment {

    @Nullable
    private int commentId;

    private int feedId;

    private int curatorId;

    @NonNull
    private String opinion;

    @Nullable
    private String name;

    @Nullable
    private String title;
}
