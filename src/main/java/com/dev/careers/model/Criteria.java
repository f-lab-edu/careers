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
    @Min(value = 0, message = "The minimum value of the offSet is 0")
    private int offset;

    @NotNull
    @Min(value = 1, message = "Limit must be set to at least 1")
    private int limit;
}
