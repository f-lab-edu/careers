package com.dev.careers.controller;


import com.dev.careers.annotation.LoginChecker;
import com.dev.careers.model.Feed;
import com.dev.careers.service.FeedService;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 피드 추가, 수정, 삭제 요청 수신
 * <p>
 * Validated 애노테이션을 클래스 레벨에 적용한 이유는 파라미터 검증을 하기위함이다. Spring boot 에서는 기본적으로
 * ValidationAutoConfiguration 클래스에서 MethodValidationPostProcessor를 빈으로 등록해서 사용하고 있다.
 * MethodValidationPostProcessor는 RquestParam, PathVariable 파라미터로 들어오는 값을 검증한다.
 * </p>
 *
 * @author junehee
 */
@AllArgsConstructor
@RestController
@Validated
public class FeedController {

    private final FeedService feedService;

    /**
     * 새로운 피드 추가
     *
     * @param feed      피드 정보
     * @param curatorId 큐레이터 아이디
     */
    @PostMapping("/curators/{curatorId}/feeds")
    @LoginChecker
    public void postFeed(@Valid @RequestBody Feed feed, @PathVariable("curatorId") int curatorId) {
        feedService.insertFeed(curatorId, feed);
    }

    /**
     * 작성한 피드 수정
     *
     * @param feed      피드 정보
     * @param curatorId 큐레이터 아이디
     */
    @PutMapping("/curators/{curatorId}/feeds")
    @LoginChecker
    public void putFeed(@Valid @RequestBody Feed feed, @PathVariable("curatorId") int curatorId) {
        feedService.updateFeed(curatorId, feed);
    }

    /**
     * 큐레이터가 작성한 피드들을 가져온다.
     *
     * @param curatorId 큐레이터 아이디
     * @return 본인이 작성한 피드 리스트
     */
    @GetMapping("/curators/{curatorId}/feeds")
    @LoginChecker
    public List<Feed> getCreatedFeeds(@PathVariable("curatorId") int curatorId) {
        return feedService.getCreatedFeeds(curatorId);
    }

    /**
     * 피드 삭제
     *
     * @param feedId 피드 아이디
     */
    @DeleteMapping("feeds/{feedId}")
    @LoginChecker
    public void deleteFeed(@PathVariable int feedId) {
        feedService.deleteFeed(feedId);
    }

    /**
     * 메인화면에서 피드 요청 시 페이징 처리하여 피드 전달
     *
     * @param offset 시작 피드 번호
     * @param limit  전달할 최대 피드 갯수
     * @return 피드 리스트 전달
     */
    @GetMapping("feeds")
    @LoginChecker
    public List<Feed> getMainFeeds(
        @Min(value = 1, message = "시작 피드 번호의 최소값은 1 입니다.") @RequestParam("offset") int offset,
        @Min(value = 1, message = "최대 전송할 피드 갯수의 최소값은 1 입니다.") @RequestParam("limit") int limit) {
        return feedService.getMainFeeds(offset, limit);
    }
}
