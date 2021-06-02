package com.dev.careers.controller;

import com.dev.careers.annotation.LoginChecker;
import com.dev.careers.model.Feed;
import com.dev.careers.service.FeedService;
import com.dev.careers.service.session.SessionAuthenticator;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 피드 추가, 수정, 삭제 요청 수신
 *
 * @author junehee
 */
@AllArgsConstructor
@RestController
public class FeedController {

    private final SessionAuthenticator sessionAuthenticator;
    private final FeedService feedService;

    /**
     * 피드를 추가 수정할 수 있다.
     * feedId에 값이 존재하지 않는 경우 = 추가
     * feedId에 값이 존재할 경우 = 수정
     *
     * @param feed 피드 정보
     * @param curatorId 큐레이터 아이디
     */
    @PutMapping("/curators/{curatorId}/feeds")
    @LoginChecker
    public void putFeed(@RequestBody Feed feed, @PathVariable("curatorId") int curatorId) {
        feedService.updateFeed(curatorId, feed);
    }

    /**
     * 설정되어있는 피드 정보를 가져온다.
     *
     * @param curatorId 큐레이터 아이디
     * @return 본인이 작성한 피드 리스트
     */
    @GetMapping("/curators/{curatorId}/feeds")
    @LoginChecker
    public List<Feed> getFeeds(@PathVariable("curatorId") int curatorId) {
        return feedService.getTotalFeeds(curatorId);
    }

    /**
     * 피드 삭제
     *
     * @param feedId 피드 아이디
     */
    @DeleteMapping("{feedId}")
    @LoginChecker
    public void deleteFeed(@PathVariable int feedId) {
        sessionAuthenticator.successLoginUserId();
        feedService.deleteFeed(feedId);
    }
}
