package com.dev.careers.service.error;

import javax.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
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
     * @return 예외 메시지와 400 Status Code
     */
    @ExceptionHandler(value = VotingNotFoundException.class)
    public ResponseEntity<String> handleVotingNotFound(RuntimeException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    /**
     * 투표 작성자 불일치시 발생하는 예외 처리 핸들러
     *
     * @param exception 투표 취소 과정 중 사용자 불일치 예외
     * @return 예외 메시지와 403 Status Code
     */
    @ExceptionHandler(value = AuthorMismatchException.class)
    public ResponseEntity<String> handleAuthorMismatch(RuntimeException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.FORBIDDEN);
    }

    /**
     * 투표 리스트 조회 페이징 처리과정에서 Cursor 범위 초과 시 발생 예외 처리 핸들러
     *
     * @param exception 투표 상세 조회 페이징 Cursor 예외
     * @return 예외 메시지와 400 Status Code
     */
    @ExceptionHandler(value = CursorOutOfRangeException.class)
    public ResponseEntity<String> handleCursorOutOfRange(RuntimeException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    /**
     * 투표 저장 처리 과정에서 발생할 수 있는 예외 처리 핸들러
     *
     * @param exception 투표 저장 처리 과정에서 발생되는 문제에 대한 예외
     * @return 예외 메세지와 400 Status Code
     */
    @ExceptionHandler(value = FailToSaveVotingException.class)
    public ResponseEntity<String> handleFailToSaveVoting(RuntimeException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    /**
     * "@ModelAttribute"나 "@RequestBody" 처리를 위해 데이터 바인딩 중에 Validation 예외 처리 핸들러
     * BindingException은 모두 MethodArgumentNotValidException을 반환
     *
     * @param exception 투표 기능 "@ModelAttribute"나 "@RequestBody" 요청 파라미터 리턴값 검증 예외
     * @return 예외 메세지와 400 Status Code
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleMethodArgumentNotValid(Exception exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    /**
     * "@Validated"가 적용된 "@RequestPram"과 "@PathVariable" 어노테이션 메서드 파라미터나 리턴 값에
     * 잘못 있으면 ConstraintViolationException 발생 예외 처리
     * 기본적으로 HTTP 500 에러로 처리하기 때문에 사용자 요청 오류인 HTTP 400으로 변경
     *
     * @param exception 투표 기능 @RequestPram"과 "@PathVariable" 요청 파라미터 리턴값 검증 예외
     * @return 예외 메시지와 400 Status Code
     */
    @ExceptionHandler(value = ConstraintViolationException.class)
    public ResponseEntity<String> handleConstraintViolation(Exception exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

}
