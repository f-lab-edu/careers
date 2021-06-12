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
     * 투표 목록 조회 후 투표 목록 반환
     *
     * @return 투표 가능한 목록
     */
    @GetMapping("/curator/votings")
    public List<Voting> getVotings() {
        return votingService.getVotings();
    }

    /**
     * 투표 상세 조회 후 해당 투표 반환
     *
     * @param id 상세 조회할 투표 아이디
     * @return voting 투표 상세 정보를 담은 투표 객체
     */
    @GetMapping("/curator/{id}/votings")
    public Voting detail(@PathVariable("id") int id) {
        return votingService.getVoting(id);
    }

    /**
     * 신규 투표 저장
     *
     * @param voting 저장할 투표 객체
     */
    @PostMapping("/curator/votings")
    public void create(@Valid @RequestBody Voting voting) {
        votingService.addVoting(voting);
    }

    /**
     * 투표 삭제 및 투표 아이템 삭제
     *
     * @param id 투표 삭제할 투표 아이디
     */
    @DeleteMapping("/curator/{id}/votings/")
    public void delete(@PathVariable("id") int id) {
        votingService.deleteVoting(id, sessionAuthenticator.successLoginUserId());
    }

}