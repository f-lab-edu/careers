package com.dev.careers.service.error;

/**
 * 이메일 중복 예외처리
 */
public class DuplicatedEmailException extends RuntimeException {

    public DuplicatedEmailException(String message) {
        super(message);
    }
}
