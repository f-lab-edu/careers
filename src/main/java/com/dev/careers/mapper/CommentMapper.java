package com.dev.careers.mapper;

import com.dev.careers.model.Comment;
import com.dev.careers.model.CommentList;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

/**
 * 댓글 Mapper
 */
@Mapper
public interface CommentMapper {

    void insertComment(Comment comment);

    void updateComment(Comment comment);

    void deleteComment(int feedId, int commentId);

    List<CommentList> getComment(int feedId);
}
