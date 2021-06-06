package com.dev.careers.service;

import com.dev.careers.mapper.CommentMapper;
import com.dev.careers.model.Comment;
import com.dev.careers.model.CommentList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 댓글 서비스
 *
 * @author junehee
 */
@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentMapper commentMapper;

    @Transactional
    public int insertComment(Comment comment) {
        commentMapper.insertComment(comment);
        return comment.getCommentId();
    }

    @Transactional
    public void updateComment(Comment comment) {
        commentMapper.updateComment(comment);
    }

    @Transactional
    public void deleteComment(int commentId) {
        commentMapper.deleteComment(commentId);
    }

    public List<CommentList> getComment(int feedId) {
        return commentMapper.getComment(feedId);
    }

}
