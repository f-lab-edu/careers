package com.dev.careers.service.error;

/**
 * 페이징 처리 시 전달받은 Cursor 범위 초과 시 발생하는 예외
 */
public class CursorOutOfRangeException extends RuntimeException {

    public CursorOutOfRangeException(String message) {
        super(message);
    }
}
