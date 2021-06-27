package com.dev.careers.mapper;

import com.dev.careers.model.Feed;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

/**
 * 피드 Mapper
 */
@Mapper
public interface FeedMapper {

    void insertFeedInfo(Feed feed);

    void updateFeedInfo(Feed feed);

    List<Feed> getFeedList(int curatorId);

    int getTotalFeedCount();

    List<Feed> getPartialFeed(int offset, int limit);

    void deleteFeed(int feedId);
}
