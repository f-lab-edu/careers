package com.dev.careers.service.error;

/**
 * 페이징 처리 시 전달받은 Offset이 최대 범위를 초과 시 발생하는 예외
 */
public class OffsetOutOfRangeException extends RuntimeException {
    public OffsetOutOfRangeException(String message) {
        super(message);
    }
}
