package com.dev.careers.service.error;

/**
 * 투표 찾기 불가 예외 처리 
 */
public class VotingNotFoundException extends RuntimeException {

    public VotingNotFoundException(int id) {
        super("Could not find Voting" + id);
    }
}
