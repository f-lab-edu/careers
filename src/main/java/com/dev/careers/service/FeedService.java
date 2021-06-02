package com.dev.careers.service;

import com.dev.careers.mapper.FeedMapper;
import com.dev.careers.model.Criteria;
import com.dev.careers.model.Feed;
import com.dev.careers.service.error.FailToSaveFeedException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 피드 관리 서비스
 *
 * @author junehee
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class FeedService {

    private final FeedMapper feedMapper;

    /**
     * 피드 추가 피드는 서버시간 기준으로 Date가 설정되며, 저장 실패 시 FailToSaveFeedException 예외가 발생한다.
     *
     * @param curatorId 큐레이터 아이디
     * @param feed      업데이트 할 피드정보
     */
    @Transactional
    public void insertFeed(int curatorId, Feed feed) {
        Date nowDate = new Date();
        Timestamp timestamp = new Timestamp(nowDate.getTime());
        Feed newFeed = new Feed(
            feed.getFeedId(),
            feed.getContent(),
            feed.getUrl(),
            timestamp,
            curatorId);

        try {
            feedMapper.insertFeedInfo(newFeed);
        } catch (Exception ex) {
            throw new FailToSaveFeedException("피드를 저장하지 못했습니다.");
        }

    }

    /**
     * 피드 갱신 피드 갱신 요청이 들어온 서버시간 기준으로 Date가 업데이트 되며, 업데이트 실패 시 FailToSaveFeedException 예외가 발생한다.
     *
     * @param curatorId 큐레이터 아이디
     * @param feed      업데이트 할 피드정보
     */
    @Transactional
    public void updateFeed(int curatorId, Feed feed) {
        Date nowDate = new Date();
        Timestamp timestamp = new Timestamp(nowDate.getTime());
        Feed updateFeed = new Feed(
            feed.getFeedId(),
            feed.getContent(),
            feed.getUrl(),
            timestamp,
            curatorId);

        try {
            feedMapper.updateFeedInfo(updateFeed);
        } catch (Exception ex) {
            throw new FailToSaveFeedException("피드를 업데이트하지 못했습니다.");
        }
    }

    /**
     * 해당 큐레이터가 작성한 모든 피드를 가져온다.
     *
     * @param curatorId 큐레이터 아이디
     * @return 작성한 피드 리스트
     */
    @Transactional
    public List<Feed> getCreatedFeeds(int curatorId) {
        return feedMapper.getFeedList(curatorId);
    }

    /**
     * 페이징 정보에 맞는 메인화면에 전달할 피드 리스트
     *
     * @param criteria 페이징 정보
     * @return 페이징 정보에 맞는 피드 리스트 전달
     */
    @Transactional
    public List<Feed> getMainFeeds(Criteria criteria) {
        int totalFeedCount = feedMapper.getTotalFeedCount();
        int maxOffset = (totalFeedCount / criteria.getLimit());
        int startOffset = criteria.getOffset() - 1;

        if (startOffset > maxOffset) {
            return null;
        }

        return feedMapper.getPartialFeed(startOffset, criteria.getLimit());
    }

    /**
     * 해당 피드 삭제
     *
     * @param feedId 피드 아이디
     */
    @Transactional
    public void deleteFeed(int feedId) {
        feedMapper.deleteFeed(feedId);
    }
}
