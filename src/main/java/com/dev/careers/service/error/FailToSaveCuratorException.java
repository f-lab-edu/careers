package com.dev.careers.service.error;

/**
 * DB에 Curator 정보 저장 실패 예외
 */
public class FailToSaveCuratorException extends RuntimeException{
    public FailToSaveCuratorException(String message){
        super(message);
    }
}
