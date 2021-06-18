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
     * 투표 요청을 찾을수 없는 경우 발생하는 예외 처리 핸들러
     *
     * @param exception 투표 상세 조회 중 요청 투표 찾기 불가 예외
     * @return 예외 메세지와 400 Status Code
     */
    @ExceptionHandler(value = VotingNotFoundException.class)
    public ResponseEntity<String> votingNotFoundException(RuntimeException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    /**
     * 투표 작성자 불일치시 발생하는 예외 처리 핸들러
     *
     * @param exception 투표 취소 과정 중 사용자 불일치 예외
     * @return 예외 메세지와 403 Status Code
     */
    @ExceptionHandler(value = AuthorMismatchException.class)
    public ResponseEntity<String> authorMismatchException(RuntimeException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.FORBIDDEN);
    }

    /**
     * 투표 리스트 조회 페이징 처리과정에서 Cursor 범위 초과 시 발생 예외 처리 핸들러
     *
     * @param exception 투표 상세 조회 페이징 Cursor 예외
     * @return 예외 메세지와 400 Status Code
     */
    @ExceptionHandler(value = CursorOutOfRangeException.class)
    public ResponseEntity<String> cursorOutOfRangeException(RuntimeException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

}
