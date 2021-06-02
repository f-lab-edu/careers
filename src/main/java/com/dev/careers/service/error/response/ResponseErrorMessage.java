package com.dev.careers.service.error.response;


import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 에러 메시지 전달 포멧
 *
 * @author junehee
 */
@AllArgsConstructor
@Getter
public class ResponseErrorMessage {

    private String code;
    private String message;

    @Override
    public String toString() {
        return "ResponseErrorMessage{"
            + "code='" + code + '\''
            + ", message='" + message + '\''
            + '}';
    }
}
