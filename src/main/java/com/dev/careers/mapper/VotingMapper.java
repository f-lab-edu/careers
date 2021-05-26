package com.dev.careers.mapper;

import com.dev.careers.model.Voting;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface VotingMapper {

    List<Voting> getVotingList();
}
