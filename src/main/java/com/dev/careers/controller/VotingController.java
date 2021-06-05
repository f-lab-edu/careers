package com.dev.careers.controller;

import com.dev.careers.model.Voting;
import com.dev.careers.service.VotingService;
import com.dev.careers.service.session.SessionAuthenticator;
import java.util.List;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 투표 관리 컨트롤러(추가, 목록, 조회, 삭제)
 *
 * @author Byeong-jun
 */
@AllArgsConstructor
@RestController
public class VotingController {

    private final VotingService votingService;

    private final SessionAuthenticator sessionAuthenticator;

    /**
     * 투표 목록 조회
     */
    @GetMapping("/curator/votings")
    public List<Voting> getVotings(){
        List<Voting> votings  = votingService.getVotings();
        return votings;
    }

    /**
     * 투표 상세 조회
     * @Param 해당 투표 아이디
     * @Return 투표 정보
     */
    @GetMapping("/curator/{id}/votings")
    public Voting detail(@PathVariable("id") int id){
        Voting voting = votingService.getVoting(id);
        return voting;
    }

    /**
     * 투표 작성
     * @Param 작성한 투표 정보
     */
    @PostMapping("/curator/votings")
    public void create(@Valid @RequestBody Voting voting){
        votingService.addVoting(voting);
    }

    /**
     * 투표 삭제
     * @Param 삭제 대상 투표 아이디
     */
    @DeleteMapping("/curator/{id}/votings/")
    public void delete(@PathVariable("id") int id){
        votingService.deleteVoting(id, sessionAuthenticator.successLoginUserId());
    }


}
