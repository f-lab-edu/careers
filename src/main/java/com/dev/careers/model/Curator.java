package com.dev.careers.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.lang.Nullable;

/**
 * Curator Model
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
public class Curator {
    
    @Nullable
    private int id;

    @NonNull
    @Email(message = "이메일 형식에 맞게 입력해주세요.")
    private String email;

    @NonNull
    private String name;

    //최소 8자리에 숫자, 문자, 특수문자 각각 1개 이상 포함
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,}$",
        message = "비밀번호를 규칙에 맞게 입력해주세요.")
    @NonNull
    private String password;

    @Nullable
    private String salt;

}
