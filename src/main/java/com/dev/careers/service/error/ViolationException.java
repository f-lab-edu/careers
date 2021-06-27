package com.dev.careers.service.error;

/**
 * 검증위반 예외처리
 */
public class ViolationException extends RuntimeException {

    public ViolationException(String message) {
        super(message);
    }
}
