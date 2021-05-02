package com.dev.careers.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

@Getter
@AllArgsConstructor
public class LoginParamter {

    @NonNull
    @Email(message = "Email Format Violation")
    private String email;

    //최소 8자리에 숫자, 문자, 특수문자 각각 1개 이상 포함
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,}$",
        message = "Password Format Violation")
    @NonNull
    private String password;
}
