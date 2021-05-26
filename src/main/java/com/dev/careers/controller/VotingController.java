package com.dev.careers.controller;

import com.dev.careers.model.Voting;
import com.dev.careers.service.VotingService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
/**
 * 투표 관리 컨트롤러
 *
 * @author junehee
 */
@AllArgsConstructor
@RestController
public class VotingController {

    private final VotingService votingService;

    @GetMapping("/votings")
    public List<Voting> getVotings(){
        List<Voting> votings  = votingService.getVotings();
        return votings;
    }
}
