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

    /**
     * 댓글을 추가한다.
     *
     * @param comment 댓글 정보
     * @return 댓글 아이디 반환
     */
    @Transactional
    public int insertComment(Comment comment) {
        commentMapper.insertComment(comment);
        return comment.getCommentId();
    }

    /**
     * 댓글 수정
     *
     * @param comment 댓글 정보
     */
    @Transactional
    public void updateComment(Comment comment) {
        commentMapper.updateComment(comment);
    }

    /**
     * 해당 피드에 속한 댓글을 삭제한다.
     *
     * @param feedId 피드 아이디
     * @param commentId 댓글 아이디
     */
    @Transactional
    public void deleteComment(int feedId, int commentId) {
        commentMapper.deleteComment(feedId, commentId);
    }

    /**
     * 해당 피드에 속한 댓글 정보들을 가져온다.
     * 만약 등록되지 않는 피드 아이디는 빈 리스트 값을 반환한다.
     *
     * @param feedId 피드 아이디
     * @return 댓글 리스트 반환
     */
    public List<Comment> getComment(int feedId) {
        return commentMapper.getComment(feedId);
    }

}
