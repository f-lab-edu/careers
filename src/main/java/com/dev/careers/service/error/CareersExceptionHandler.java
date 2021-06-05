package com.dev.careers.service.error;

import com.dev.careers.service.error.response.ResponseErrorMessage;
import javax.validation.ConstraintViolationException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Curator 예외처리 핸들러
 *
 * @author junehee
 */
@Log4j2
@RestControllerAdvice
public class CareersExceptionHandler {

    /**
     * 회원가입 시 이메일 중복으로 인한 예외 처리 로그인 시 비밀번호 불일치로 인한 예외 처리
     *
     * @param ex 올바르지 않는 요청에 대한 결과
     * @return 상태코드 및 에러 메시지
     */
    @ExceptionHandler(value = {DuplicatedEmailException.class, ViolationException.class})
    public ResponseEntity<ResponseErrorMessage> badRequest(final RuntimeException ex) {
        ResponseErrorMessage responseErrorMessage = new ResponseErrorMessage(
            HttpStatus.BAD_REQUEST.toString(),
            ex.getMessage()
        );

        log.error(responseErrorMessage);
        return new ResponseEntity<>(responseErrorMessage, HttpStatus.BAD_REQUEST);
    }

    /**
     * 서버에서 사용할 수 없는 암호화 알고리즘 사용시 예외 처리
     *
     * @param ex 올바르지 않는 요청에 대한 결과
     * @return 상태코드 및 에러 메시지
     */
    @ExceptionHandler(value = NotSupportAlgorithmException.class)
    public ResponseEntity<ResponseErrorMessage> passwordAlgorithmException(
        final RuntimeException ex) {
        ResponseErrorMessage responseErrorMessage = new ResponseErrorMessage(
            HttpStatus.INTERNAL_SERVER_ERROR.toString(),
            ex.getMessage()
        );

        log.error(responseErrorMessage);

        return new ResponseEntity<>(responseErrorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * 큐레이터, 피드 저장 실패 시 해당 예외 발생
     *
     * @param ex 올바르지 않는 요청에 대한 결과
     * @return 상태코드 및 에러 메시지
     */
    @ExceptionHandler(value = {FailToSaveFeedException.class, FailToSaveCuratorException.class})
    public ResponseEntity<ResponseErrorMessage> failToSaveException(final RuntimeException ex) {
        ResponseErrorMessage responseErrorMessage = new ResponseErrorMessage(
            HttpStatus.INTERNAL_SERVER_ERROR.toString(),
            ex.getMessage()
        );

        log.error(responseErrorMessage);

        return new ResponseEntity<>(responseErrorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Controller에서 Valid 애노테이션으로 걸린 BindingException은 모두 MethodArgumentNotValidException을 반환한다.
     * MethodArgumentNotValidException 예외는 모두 서버에 잘못된 요청임으로 HttpStatus 코드는 BAD_REQUEST로 반환한다.
     *
     * @param ex 올바르지 않는 요청에 대한 결과
     * @return 상태코드 및 에러 메시지
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseErrorMessage> argumentNotValidException(
        MethodArgumentNotValidException ex) {

        ResponseErrorMessage responseErrorMessage = new ResponseErrorMessage(
            HttpStatus.BAD_REQUEST.toString(),
            ex.getBindingResult().getFieldError().getDefaultMessage());

        log.error(responseErrorMessage);

        return new ResponseEntity<>(responseErrorMessage, HttpStatus.BAD_REQUEST);
    }

    /**
     * RequestParam, PathVariable에서 파라미터 검증 위반한 경우 ConstraintViolationException 예외가 발생합니다.
     * Springboot에서는 ConstraintViolationException 예외를 500 Error로 취급합니다. 하지만 사용자 요청이 잘못된 것 이므로 예외를
     * 400 Error로 전환합니다.
     *
     * @param ex 올바르지 않는 파라미터 요청에 대한 결과
     * @return 상태코드 및 에러 메시지
     */
    @ExceptionHandler(value = ConstraintViolationException.class)
    public ResponseEntity<ResponseErrorMessage> constraintViolationException(
        ConstraintViolationException ex) {

        ResponseErrorMessage responseErrorMessage = new ResponseErrorMessage(
            HttpStatus.BAD_REQUEST.toString(),
            ex.getMessage());

        log.error(responseErrorMessage);

        return new ResponseEntity<>(responseErrorMessage, HttpStatus.BAD_REQUEST);
    }
}
