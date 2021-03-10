package com.dev.careers.model;

import lombok.Data;
import lombok.NonNull;
import org.springframework.lang.Nullable;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;


@Data
public class Curator {
    @Nullable
    private int id;
    
    @NonNull
    @Email(message = "Email Format Violation")
    private String email;
    
    @NonNull
    private String name;

    //최소 8자리에 숫자, 문자, 특수문자 각각 1개 이상 포함
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,}$",
            message = "Password Format Violation")
    @NonNull
    private String password;
    
    @Nullable
    private String salt;
}
