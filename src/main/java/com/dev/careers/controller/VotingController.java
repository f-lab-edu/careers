package com.dev.careers.controller;

import com.dev.careers.model.Voting;
import com.dev.careers.service.VotingService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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

    /**
     * 투표 목록 조회
     */
    @GetMapping("/votings")
    public List<Voting> getVotings(){
        List<Voting> votings  = votingService.getVotings();
        return votings;
    }

    /**
     * 투표 상세 조회
     */
    @GetMapping("/votings/{id}")
    public Voting detail(@PathVariable("id") int id){
        Voting voting = votingService.getVoting(id);
        return voting;
    }

}
