package com.dev.careers.service;

import com.dev.careers.mapper.FeedMapper;
import com.dev.careers.model.Feed;
import com.dev.careers.service.error.FailToSaveFeedException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FeedServiceTest {

    @InjectMocks
    FeedService feedService;

    @Mock
    FeedMapper feedMapper;

    @Test
    @DisplayName("피드 추가")
    public void insertFeed(){
        doNothing()
            .when(feedMapper)
            .insertFeedInfo(any(Feed.class));

        int curatorId = 1;
        Feed feed = new Feed();
        feed.setContent("피드 내용 추가");
        feed.setUrl("www.naver.com");

        feedService.updateFeed(curatorId, feed);

        verify(feedMapper, times(1))
            .insertFeedInfo(any(Feed.class));
    }

    @Test
    @DisplayName("피드 삭제")
    public void deleteFeed(){
        assertDoesNotThrow(() -> feedService.deleteFeed(2));
    }

    @Test
    @DisplayName("피드 내용없이 추가요청 시 실패")
    public void insertFeedFailToNotContent(){
        doThrow(new FailToSaveFeedException("피드 내용이 없어 실패"))
            .when(feedMapper)
            .insertFeedInfo(any(Feed.class));

        int curatorId = 1;
        Feed feed = new Feed();
        feed.setUrl("www.naver.com");

        assertThrows(FailToSaveFeedException.class,
            () -> feedService.updateFeed(curatorId, feed));

        verify(feedMapper, times(1))
            .insertFeedInfo(any(Feed.class));
    }
}
