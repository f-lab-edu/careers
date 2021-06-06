package com.dev.careers.service.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 투표 예외 통합 관리 클래스
 */
@RestControllerAdvice
public class VotingExceptionHandler {

    /**
     * 요청을 찾을수 없는 경우 발생하는 예외 처리 핸들러
     */
    @ExceptionHandler(value = VotingNotFoundException.class)
    public ResponseEntity<String> NotFoundException(RuntimeException exception){
        return new ResponseEntity<String>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    /**
     * 투표 관련 비교 기능 수행 시 발생하는 예외 처리 핸들러
     */
    @ExceptionHandler(value = AuthorMismatchException.class)
    public ResponseEntity<String> mismatchException(RuntimeException exception){
        return new ResponseEntity<String>(exception.getMessage(), HttpStatus.FORBIDDEN);
    }

}
