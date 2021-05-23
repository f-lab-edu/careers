package com.dev.careers.controller;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 피드 추가, 수정, 삭제 요청 수신
 *
 * @author junehee
 */
@AllArgsConstructor
@RestController
@RequestMapping("feeds")
public class FeedController {

    private final SessionAuthenticator sessionAuthenticator;
    private final FeedService feedService;

    @PutMapping
    public void putFeed(@RequestBody Feed feed) {
        int curatorId = sessionAuthenticator.successLoginUserId();
        feedService.updateFeed(curatorId, feed);
    }

    @GetMapping
    public List<Feed> getFeeds() {
        int curatorId = sessionAuthenticator.successLoginUserId();
        return feedService.getTotalFeeds(curatorId);
    }

    @DeleteMapping("{feedId}")
    public void deleteFeed(@PathVariable int feedId) {
        sessionAuthenticator.successLoginUserId();
        feedService.deleteFeed(feedId);
    }
}
