package com.dev.careers.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.dev.careers.mapper.VotingItemMapper;
import com.dev.careers.mapper.VotingMapper;
import com.dev.careers.model.Voting;
import com.dev.careers.model.VotingItem;
import com.dev.careers.service.error.AuthorMismatchException;
import com.dev.careers.service.error.CursorOutOfRangeException;
import com.dev.careers.service.error.FailToSaveVotingException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

/**
 * 투표 관리 서비스 테스트
 *
 * @author Byeong-jun
 */

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class VotingServiceTest {

    VotingService votingService;

    @Mock
    VotingMapper votingMapper;

    @Mock
    VotingItemMapper votingItemMapper;

    /**
     * Mock 객체 초기화
     */
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockVotingMapper();
        mockVotingItemMapper();
        votingService = new VotingService(10, this.votingMapper, this.votingItemMapper);
    }

    /**
     * Mock 객체 초기화 종료
     *
     * @throws Exception 리소스를 닫을 수 없는 경우 Exception 처리
     */
    @AfterEach
    public void close() throws Exception {
        MockitoAnnotations.openMocks(this).close();
    }

    /**
     * MockVotingMapper 초기화
     */
    public void mockVotingMapper() {
        List<Voting> votings = new ArrayList<Voting>();
        LocalDateTime localDateTime = LocalDateTime.now();
        Timestamp timestamp = Timestamp.valueOf(localDateTime);
        Timestamp deadline = Timestamp.valueOf(localDateTime.plusDays(7));
        Voting voting = Voting.builder()
            .votingId(1)
            .votingTitle("Test")
            .votingWriter(1)
            .timestamp(timestamp)
            .deadline(deadline)
            .votingExplanation("getVoting_Test")
            .build();
        votings.add(voting);

        given(votingMapper.getTotalVotingCount()).willReturn(1);
        given(votingMapper.getVotingList(1, 10)).willReturn(votings);

        given(votingMapper.getVoting(1)).willReturn(Optional.of(voting));
    }

    /**
     * MockVotingItemMapper 초기화
     */
    public void mockVotingItemMapper() {
        List<VotingItem> votingItems = new ArrayList<VotingItem>();
        VotingItem votingItem = VotingItem.builder()
            .votingItemId(1)
            .votingId(1)
            .votingItemName("testItem")
            .voteCount(1)
            .build();
        votingItems.add(votingItem);

        given(votingItemMapper.getVotingItemList(1)).willReturn(votingItems);
    }

    @Test
    @DisplayName("정상적인 투표 목록 조회 처리")
    public void getVotings_ValidData_True() {
        List<Voting> votings = votingService.getVotings(1);

        verify(votingMapper, times(1)).getTotalVotingCount();
        verify(votingMapper, times(1)).getVotingList(anyInt(), anyInt());

        Voting voting = votings.get(0);

        assertThat(voting.getVotingId(), is(1));
    }

    @Test
    @DisplayName("cursor가 현재 저장된 투표수보다 높은 숫자인 경우 투표 목록 조회 처리")
    public void getVotings_InvalidData_ExceptionThrown() {
        int cursor = 2;
        CursorOutOfRangeException exception = assertThrows(CursorOutOfRangeException.class,
            () -> votingService.getVotings(cursor));

        String message = exception.getMessage();
        assertEquals("투표 목록 조회 범위를 초과하였습니다.", message);
        verify(votingMapper, times(0)).getVotingList(anyInt(), anyInt());
    }

    @Test
    @DisplayName("정상적인 투표 상세 조회 처리")
    public void getVoting_ValidData_True() {
        Voting voting = votingService.getVoting(1);

        verify(votingItemMapper, times(1)).getVotingItemList(eq(1));

        assertThat(voting.getVotingId(), is(1));

        VotingItem votingItem = voting.getVotingItems().get(0);

        assertThat(votingItem.getVotingId(), is(1));
        assertThat(votingItem.getVotingItemName(), is("testItem"));
    }

    @Test
    @DisplayName("정상적인 데이터 형식 투표 생성 처리")
    public void addVoting_ValidData_True() {
        List<VotingItem> votingItems = new ArrayList<VotingItem>();
        LocalDateTime localDateTime = LocalDateTime.now();
        Timestamp timestamp = Timestamp.valueOf(localDateTime);
        Timestamp deadline = Timestamp.valueOf(localDateTime.plusDays(7));
        VotingItem votingItem = VotingItem.builder()
            .votingItemId(1)
            .votingId(1)
            .votingItemName("testItem")
            .voteCount(1)
            .build();
        votingItems.add(votingItem);

        Voting voting = Voting.builder()
            .votingId(1)
            .votingTitle("Test")
            .votingWriter(1)
            .timestamp(timestamp)
            .deadline(deadline)
            .votingExplanation("getVoting_Test")
            .votingItems(votingItems)
            .build();

        willDoNothing().given(votingMapper).saveVoting(voting);
        willDoNothing().given(votingItemMapper).saveVotingItems(votingItems);

        votingService.addVoting(voting);

        verify(votingMapper, times(1)).saveVoting(any(Voting.class));

        verify(votingItemMapper, times(1)).saveVotingItems(anyList());
    }

    @Test
    @DisplayName("투표 생성 처리 과정에서 투표를 저장할 수 없는 상황 처리")
    public void addVoting_InvalidData_ExceptionThrown() {
        List<VotingItem> votingItems = new ArrayList<VotingItem>();
        LocalDateTime localDateTime = LocalDateTime.now();
        Timestamp timestamp = Timestamp.valueOf(localDateTime);
        Timestamp deadline = Timestamp.valueOf(localDateTime.plusDays(7));
        VotingItem votingItem = VotingItem.builder()
            .votingItemId(1)
            .votingId(1)
            .votingItemName("testItem")
            .voteCount(1)
            .build();
        votingItems.add(votingItem);

        Voting voting = Voting.builder()
            .votingTitle("Test")
            .votingWriter(1)
            .timestamp(timestamp)
            .deadline(deadline)
            .votingExplanation("getVoting_Test")
            .votingItems(votingItems)
            .build();

        willThrow(new FailToSaveVotingException("투표를 저장할 수 없습니다."))
            .given(votingMapper)
            .saveVoting(voting);

        FailToSaveVotingException exception = assertThrows(FailToSaveVotingException.class,
            () -> votingMapper.saveVoting(voting));

        assertEquals("투표를 저장할 수 없습니다.", exception.getMessage());
        verify(votingMapper, times(1)).saveVoting(any(Voting.class));
        verify(votingItemMapper, times(0)).saveVotingItems(anyList());
    }


    @Test
    @DisplayName("정상적인 투표 제거 처리")
    public void deleteVoting_ValidData_True() {
        given(votingMapper.getVotingWriter(1)).willReturn(1);
        willDoNothing().given(votingMapper).removeVoting(1);
        willDoNothing().given(votingItemMapper).removeVotingItems(1);

        votingService.deleteVoting(1, 1);

        assertThat(votingMapper.getVotingWriter(1), is(1));

        verify(votingMapper).removeVoting(anyInt());

        verify(votingItemMapper).removeVotingItems(anyInt());
    }

    @Test
    @DisplayName("삭제할 투표 작성자와 삭제 요청자가 일치하지 않을 경우에 처리")
    public void deleteVoting_InvalidData_ExceptionThrows() {
        AuthorMismatchException exception = assertThrows(AuthorMismatchException.class,
            () -> votingService.deleteVoting(1, 1));

        String message = exception.getMessage();

        assertEquals("해당 요청자와 투표 작성자는 일치하지 않습니다.", message);

        verify(votingMapper, times(0)).removeVoting(anyInt());

        verify(votingItemMapper, times(0)).removeVotingItems(anyInt());
    }

}
