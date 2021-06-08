package com.dev.careers.controller;

import com.dev.careers.annotation.LoginChecker;
import com.dev.careers.model.Comment;
import com.dev.careers.model.CommentList;
import com.dev.careers.service.CommentService;
import java.util.List;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 댓글 처리
 *
 * @author junehee
 */
@RestController
@AllArgsConstructor
public class CommentController {

    private final CommentService commentService;

    /**
     * 댓글 작성
     *
     * @param comment 작성한 댓글
     * @return 추가한 댓글 아이디
     */
    @PostMapping("comments")
    @LoginChecker
    public int postComment(@Valid @RequestBody Comment comment) {

        return commentService.insertComment(comment);
    }

    /**
     * 댓글 수정
     *
     * @param comment 수정한 댓글
     */
    @PutMapping("comments")
    @LoginChecker
    public void putComment(@Valid @RequestBody Comment comment) {

        commentService.updateComment(comment);
    }

    /**
     * 댓글 삭제
     *
     * @param feedId    피드 아이디
     * @param commentId 삭제 할 댓글 아이디
     */
    @DeleteMapping("feeds/{feedId}/comments/{commentId}")
    @LoginChecker
    public void deleteComment(@PathVariable("feedId") int feedId,
        @PathVariable("commentId") int commentId) {

        commentService.deleteComment(feedId, commentId);
    }

    /**
     * 해당 피드에 달린 모든 댓글 가져오기
     *
     * @param feedId 피드 아이디
     * @return 피드에 달린 모든 댓글
     */
    @GetMapping("comments/{feedId}")
    @LoginChecker
    public List<CommentList> getComments(@PathVariable("feedId") int feedId) {

        return commentService.getComment(feedId);
    }

}
