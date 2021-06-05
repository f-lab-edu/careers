package com.dev.careers.service.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class VotingExceptionHandler {

    @ExceptionHandler(value = VotingNotFoundException.class)
    public ResponseEntity<String> NotFoundException(RuntimeException exception){
        return new ResponseEntity<String>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = AuthorMismatchException.class)
    public ResponseEntity<String> mismatchException(RuntimeException exception){
        return new ResponseEntity<String>(exception.getMessage(), HttpStatus.FORBIDDEN);
    }

}
