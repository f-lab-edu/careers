package com.dev.careers.model;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Criteria Model
 */
@Getter
@RequiredArgsConstructor
public class Criteria {

    @NotNull
    @Min(value = 1, message = "시작 피드 번호의 최소값은 1 입니다.")
    private int offset;

    @NotNull
    @Min(value = 1, message = "최대 전송할 피드 갯수의 최소값은 1 입니다.")
    private int limit;
}
