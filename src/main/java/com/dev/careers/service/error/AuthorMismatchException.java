package com.dev.careers.service.error;

/**
 * 작성자 불일치 예외 처리
 */
public class AuthorMismatchException extends RuntimeException {

    public AuthorMismatchException(String message) {
        super(message);
    }
}
