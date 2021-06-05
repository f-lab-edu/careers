package com.dev.careers.service.error;

/**
 * 투표를 정상적으로 저장하지 못할 경우 예외 처리
 */
public class FailToSaveVotingException extends RuntimeException{

    public FailToSaveVotingException(String message){
        super(message);
    }
}