package com.dev.careers.model;

import lombok.Getter;
import lombok.Setter;

/**
 * CommentList Model
 * Mybatis에서 값을 설정하기위해 Getter, Setter 애노테이션 적용
 */
@Setter
@Getter
public class CommentList {

    private int feedId;

    private int commentId;

    private String name;

    private String title;

    private String opinion;
}
