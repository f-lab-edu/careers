package com.dev.careers.mapper;

import com.dev.careers.model.Voting;
import java.util.List;
import java.util.Optional;
import org.apache.ibatis.annotations.Mapper;

/**
 * 투표 Mapper
 */
@Mapper
public interface VotingMapper {

    int getTotalVotingCount();

    List<Voting> getVotingList(int limit, int offset);

    Optional<Voting> getVoting(int votingId);

    int getVotingWriter(int votingId);

    void saveVoting(Voting voting);

    void removeVoting(int votingId);
}
