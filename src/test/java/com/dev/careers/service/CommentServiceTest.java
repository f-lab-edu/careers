package com.dev.careers.service;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.dev.careers.mapper.CommentMapper;
import com.dev.careers.model.Comment;
import java.util.ArrayList;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Mockito의 Mock객체를 사용하기 위해서 Junit5부터 사용되는 ExtendWith 애노테이션 사용
 */
@ExtendWith(MockitoExtension.class)
class CommentServiceTest {

    @InjectMocks
    CommentService commentService;

    @Mock
    CommentMapper commentMapper;

    @DisplayName("댓글 등록에 성공하면 DB에 값이 저장된다.")
    @Test
    public void insertCommentTest() {
        //Arrage
        Comment comment = new Comment();
        comment.setCommentId(1);
        comment.setFeedId(1);
        comment.setCuratorId(1);
        comment.setOpinion("test");

        doNothing().when(commentMapper).insertComment(comment);
        //Act
        commentService.insertComment(comment);
        //Assert
        verify(commentMapper).insertComment(comment);
    }

    @DisplayName("댓글 삭제 시 DB에 댓글 데이터가 삭제된다.")
    @Test
    public void deleteCommentTest() {
        //Arrange
        int feedId = 1;
        int commentId = 1;
        doNothing().when(commentMapper).deleteComment(feedId, commentId);
        //Act
        commentService.deleteComment(feedId, commentId);
        //Assert
        verify(commentMapper).deleteComment(feedId, commentId);
    }

    @DisplayName("등록되지 않은 피드 아이디로 댓글 조회 시 비어있는 리스트를 반환한다.")
    @Test
    public void test() {
        //Arrange
        when(commentMapper.getComment(anyInt())).thenReturn(new ArrayList<>());
        //Act
        commentService.getComment(3);
        //Assert
        verify(commentMapper).getComment(anyInt());
    }

}