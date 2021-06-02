package com.dev.careers.service;

import com.dev.careers.mapper.FeedMapper;
import com.dev.careers.model.Feed;
import com.dev.careers.service.error.SqlInsertException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FeedServiceTest {

    @InjectMocks
    FeedService feedService;

    @Mock
    FeedMapper feedMapper;

    @Test
    @DisplayName("피드 추가에 성공하면 데이터베이스에 값이 저장된다.")
    public void insertFeed() {
        doNothing()
                .when(feedMapper)
                .insertFeedInfo(any(Feed.class));

        int curatorId = 1;
        Feed feed = new Feed(
                0,
                "피드 내용 추가",
                "www.naver.com",
                null,
                0
        );

        feedService.updateFeed(curatorId, feed);

        verify(feedMapper, times(1))
                .insertFeedInfo(any(Feed.class));
    }

    @Test
    @DisplayName("피드 삭제에 성공하면 데이터베이스에 피드가 삭제된다.")
    public void deleteFeed() {
        doNothing().when(feedMapper).deleteFeed(anyInt());

        feedService.deleteFeed(anyInt());

        verify(feedMapper).deleteFeed(anyInt());
    }

    @Test
    @DisplayName("피드 내용없이 추가요청 시 SqlInsertException 예외가 발생한다.")
    public void insertFeedFailToNotContent() {
        doThrow(new SqlInsertException("피드 내용이 없어 실패"))
                .when(feedMapper)
                .insertFeedInfo(any(Feed.class));

        int curatorId = 1;
        Feed feed = new Feed(
                0,
                null,
                "www.naver.com",
                null,
                0
        );

        assertThrows(SqlInsertException.class,
                () -> feedService.updateFeed(curatorId, feed));

        verify(feedMapper).insertFeedInfo(any(Feed.class));
    }
}
