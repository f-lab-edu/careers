package com.dev.careers.service.error;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Curator 예외처리 핸들러
 */
@Log4j2
@RestControllerAdvice
public class CuratorExceptionHandler {

    @ExceptionHandler(value = {DuplicatedEmailException.class, ViolationException.class})
    public ResponseEntity<String> badRequest(final RuntimeException ex) {
        log.error(ex.getMessage());
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = SqlInsertException.class)
    public ResponseEntity<String> sqlError(final RuntimeException ex) {
        log.error(ex.getMessage());
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = NotSupportAlgorithmException.class)
    public ResponseEntity<String> passwordAlgorithmException(final RuntimeException ex) {
        log.error(ex.getMessage());
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
