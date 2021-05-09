package com.dev.careers.service.error;

public class SqlInsertException extends RuntimeException {
    public SqlInsertException(String message) {
        super(message);
    }
}
