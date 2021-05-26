package com.dev.careers.service;

import com.dev.careers.mapper.FeedMapper;
import com.dev.careers.model.Criteria;
import com.dev.careers.model.Feed;
import com.dev.careers.service.error.SqlInsertException;
import java.util.Date;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 피드 관리 서비스
 *
 * @author junehee
 */
@Log4j2
@RequiredArgsConstructor
@Service
public class FeedService {

    private final FeedMapper feedMapper;

    /**
     * 피드 추가, 갱신을 처리(feedId값을 기준) feedId = 0 피드 추가 feedId > 1 피드 갱신
     *
     * @param curatorId 큐레이터 아이디
     * @param feed      업데이트 할 피드정보
     */
    @Transactional
    public void updateFeed(int curatorId, Feed feed) {
        java.util.Date nowDate = new Date();
        java.sql.Timestamp timestamp = new java.sql.Timestamp(nowDate.getTime());
        feed.setDate(timestamp);
        feed.setCuratorId(curatorId);

        try {
            if (feed.getFeedId() > 0) {
                feedMapper.updateFeedInfo(feed);
            } else {
                feedMapper.insertFeedInfo(feed);
            }
        } catch (Exception ex) {
            log.warn(ex.getMessage());
            throw new SqlInsertException("피드 컨텐츠가 없어 정보를 저장하지 못했습니다.");
        }
    }

    /**
     * 해당 큐레이터가 작성한 모든 피드를 가져온다.
     *
     * @param curatorId 큐레이터 아이디
     * @return 작성한 피드 리스트
     */
    @Transactional
    public List<Feed> getTotalFeeds(int curatorId) {
        return feedMapper.getFeedList(curatorId);
    }

    /**
     * 메인화면에 전달받은 페이징 정보에 맞게 피드 리스트를 전달.
     *
     * @param criteria 페이징 정보
     * @return 피드 리스트 전달
     */
    @Transactional
    public List<Feed> getMainFeeds(Criteria criteria) {
        int totalFeedCount = feedMapper.getTotalFeedCount();
        int maxOffset = (totalFeedCount / criteria.getLimit());

        if (criteria.getOffset() <= maxOffset) {
            return feedMapper.getPartialFeed(criteria.getOffset(), criteria.getLimit());
        } else {
            throw new RuntimeException("전달 데이터가 없습니다.");
        }
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
