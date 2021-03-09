package com.dev.careers.service.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CuratorExceptionHandler {

    @ExceptionHandler({DuplicatedEmailException.class})
    public ResponseEntity<HttpStatus> badRequest(final DuplicatedEmailException ex) {
        return new ResponseEntity(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
