package com.dev.careers.controller;

import com.dev.careers.model.VotingItem;
import com.dev.careers.service.VotingItemService;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 투표 아이템 컨트롤러(변경)
 *
 * @author Byeong-jun
 */
@AllArgsConstructor
@RestController
public class VotingItemController {

    private final VotingItemService votingItemService;

    /**
     * 투표 아이템 투표 갯수 변경
     *
     * @param votingItem 갯수 증가 시킬 투표 아이템 객체
     */
    @PostMapping("/curator/VotingItems")
    public void countUpdate(@Valid @RequestBody VotingItem votingItem) {
        votingItemService.countUpdate(votingItem);
    }
}
