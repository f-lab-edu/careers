package com.dev.careers.service;

import com.dev.careers.mapper.VotingItemMapper;
import com.dev.careers.mapper.VotingMapper;
import com.dev.careers.model.Voting;
import com.dev.careers.model.VotingItem;
import com.dev.careers.service.error.AuthorMismatchException;
import com.dev.careers.service.error.CursorOutOfRangeException;
import com.dev.careers.service.error.FailToSaveVotingException;
import com.dev.careers.service.error.VotingNotFoundException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * 투표 관리 서비스
 *
 * @author Byeong-jun
 */

@Service
@AllArgsConstructor
public class VotingService {

    private final VotingMapper votingMapper;

    private final VotingItemMapper votingItemMapper;

    @Value("${voting.limit}")
    private final int limit;

    /**
     * 투표 가능한 투표들 조희 후 투표 목록 반환
     * cursor 0일 경우 투표 전체 개수를 할당하여 투표 목록 조회 시작점으로 설정 후 가장 최근 목록 조회
     *
     * @param cursor 페이징 처리된 투표 리스트 현재 위치 ID
     * @return List 투표 가능한 목록
     */
    @Transactional
    public List<Voting> getVotings(int cursor) {
        int votingTotalCount = votingMapper.getTotalVotingCount();

        if (cursor == 0) {
            cursor = votingTotalCount;
        }
        if (votingTotalCount < cursor) {
            throw new CursorOutOfRangeException("Cursor 범위가 초과하였습니다.");
        }

        return votingMapper.getVotingList(cursor, limit).stream().collect(Collectors.toList());
    }

    /**
     * 투표 상세 조회 후 해당 투표 반환
     *
     * @param id 상세 조회할 투표 아이디
     * @return 투표 상세 정보를 담은 투표 객체
     */
    @Transactional
    public Voting getVoting(int id) {
        Voting voting = votingMapper.getVoting(id).orElseThrow(
            () -> new VotingNotFoundException(id));
        List<VotingItem> votingItems = votingItemMapper.getVotingItemList(id);
        Voting getResultVoting = new Voting(voting, votingItems);
        return getResultVoting;
    }

    /**
     * 신규 투표 저장, 해당 투표 아이템 저장
     *
     * @param voting 저장할 투표 정보 객체
     */
    @Transactional
    public void addVoting(Voting voting) {
        LocalDateTime today = LocalDateTime.now();
        Timestamp timestamp = Timestamp.valueOf(today);
        Timestamp deadline = Timestamp.valueOf(today.plusDays(7));

        Voting addedTimeVoting = new Voting(voting, timestamp, deadline);

        try {
            votingMapper.saveVoting(addedTimeVoting);
            votingItemMapper.saveVotingItems(addedTimeVoting.getVotingItems());
        } catch (Exception exception) {
            throw new FailToSaveVotingException("투표를 저장할 수 없습니다.");
        }
    }

    /**
     * 투표 취소, 해당 투표 아이템 삭제
     *
     * @param votingId 삭제 대상 투표 아이디
     * @param votingWriter 삭제 대상 투표 작성자
     */
    @Transactional
    public void deleteVoting(int votingId, int votingWriter) {
        if (votingWriter == votingMapper.getVotingWriter(votingId)) {
            votingMapper.removeVoting(votingId);
            votingItemMapper.removeVotingItems(votingId);
        } else {
            throw new AuthorMismatchException("해당 요청자와 투표 작성자는 일치하지 않습니다.");
        }
    }
}
