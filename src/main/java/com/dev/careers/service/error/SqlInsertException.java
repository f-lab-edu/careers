package com.dev.careers.service.error;

/**
 * 회원가입 시 DB insert 실패에 대한 예외처리
 */
public class SqlInsertException extends RuntimeException {
    public SqlInsertException(String message) {
        super(message);
    }
}
