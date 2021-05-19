package com.dev.careers.service.error;

/**
 * 비밀번호 암호화 실패에 대한 예외처리
 */
public class NotSupportAlgorithmException extends RuntimeException {

    public NotSupportAlgorithmException(String message) {
        super(message);
    }
}
