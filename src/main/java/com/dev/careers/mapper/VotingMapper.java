package com.dev.careers.mapper;

import com.dev.careers.model.Voting;
import java.util.List;
import java.util.Optional;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface VotingMapper {

    List<Voting> getVotingList();

    Optional<Voting> getVoting(int votingId);

    void saveVoting(Voting voting);

    void removeVoting(int votingId);

    int getVotingWriter(int votingId);
}
