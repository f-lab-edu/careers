package com.dev.careers.controller;

import com.dev.careers.model.Criteria;
import com.dev.careers.model.Feed;
import com.dev.careers.service.FeedService;
import com.dev.careers.service.error.ViolationException;
import com.dev.careers.service.session.SessionAuthenticator;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    public List<Feed> getCreatedFeeds() {
        int curatorId = sessionAuthenticator.successLoginUserId();
        return feedService.getCreatedFeeds(curatorId);
    }

    @DeleteMapping("{feedId}")
    public void deleteFeed(@PathVariable int feedId) {
        sessionAuthenticator.successLoginUserId();
        feedService.deleteFeed(feedId);
    }

    /**
     * 메인화면에서 피드 요청 시 페이징 처리하여 피드 전달
     * limit 요청 당 전달할 최대 피드 갯수 (최소값 : 1 )
     * offset 시작 피드 번호 (최소값 : 0)
     *
     * @param criteria 페이징 정보
     * @param bindingResult 페이징 정보 검증결과
     * @return 피드 리스트 전달
     */
    @GetMapping("main")
    public List<Feed> getMainFeeds(@Valid @RequestBody Criteria criteria,
        BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Optional<ObjectError> objectError = bindingResult.getAllErrors().stream().findFirst();
            if (objectError.isPresent()) {
                throw new ViolationException();
            }
        }

        sessionAuthenticator.successLoginUserId();
        return feedService.getMainFeeds(criteria);
    }
}
