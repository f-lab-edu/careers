package com.dev.careers.service.error;

public class ViolationException extends RuntimeException{

    public ViolationException(String message) {
        super(message);
    }
}
