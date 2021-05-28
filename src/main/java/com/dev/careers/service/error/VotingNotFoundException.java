package com.dev.careers.service.error;

public class VotingNotFoundException extends RuntimeException{

    public VotingNotFoundException(int id){
        super("Could not find Voting" + id);
    }
}
