package com.dev.careers.service.error;

/**
 *
 */
public class AuthorMismatchException extends RuntimeException{

    public AuthorMismatchException(String message){
        super(message);
    }
}
