package com.dev.careers.service.error;

/**
 * DB에 피드 저장 실패 예외
 */
public class FailToSaveFeedException extends RuntimeException {

    public FailToSaveFeedException(String message) {
        super(message);
    }
}
