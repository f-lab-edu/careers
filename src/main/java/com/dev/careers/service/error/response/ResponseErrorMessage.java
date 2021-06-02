package com.dev.careers.service.error.response;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ResponseErrorMessage {
    private String code;
    private String message;

    @Override
    public String toString() {
        return "ResponseErrorMessage{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
