package com.dev.careers.domain;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Curator {

    private Long id;

    @NotNull
    private String name;

    @NotNull
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,}$",
        message = "The password is not in the correct format.")
    private String password;

    @NotNull
    @Email(message = "Email with invalid form.")
    private String email;
}
