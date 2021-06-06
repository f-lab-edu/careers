package com.dev.careers.controller;

import com.dev.careers.model.VotingItem;
import com.dev.careers.service.VotingItemService;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
public class VotingItemController {

    private VotingItemService votingItemService;

    @PostMapping("/curator/{id}/VotingItems")
    public void countUpdate(@Valid @PathVariable("votingId") int votingId,
        @RequestParam("votingItemName") String votingItemName){
        votingItemService.countUpdate(votingId, votingItemName);
    }
}
