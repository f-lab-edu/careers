package com.dev.careers.service;

import com.dev.careers.model.Curator;
import com.dev.careers.model.LoginParamter;
import com.dev.careers.service.error.DuplicatedEmailException;
import com.dev.careers.service.error.ViolationException;
import java.security.NoSuchAlgorithmException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest
class CuratorServiceTest {

    @Autowired
    CuratorService curatorService;

    @Test
    @DisplayName("중복된 이메일 회원가입 요청")
    public void dupicatedEmail() throws Exception {
        Curator curator = new Curator(
            "test@google.com",
            "홍길동",
            "test123!@"
        );
        curatorService.join(curator);
        org.junit.jupiter.api.Assertions.assertThrows(
            DuplicatedEmailException.class,
            () -> curatorService.join(curator));
    }

    @Test
    @DisplayName("로그인 성공")
    public void successLogin() throws NoSuchAlgorithmException {
        Curator curator = new Curator(
            "test@google.com",
            "홍길동",
            "test123!@"
        );
        curatorService.join(curator);

        LoginParamter paramter = new LoginParamter(
            "test@google.com",
            "test123!@"
        );

        org.junit.jupiter.api.Assertions.assertDoesNotThrow(
            () -> curatorService.login(paramter));
    }

    @Test
    @DisplayName("비밀번호 오류로 인한 로그인 실패")
    public void failToLogin() throws NoSuchAlgorithmException {
        Curator curator = new Curator(
            "test@google.com",
            "홍길동",
            "test123!@"
        );
        curatorService.join(curator);

        LoginParamter paramter = new LoginParamter(
            "test@google.com",
            "test445@#"
        );
        org.junit.jupiter.api.Assertions.assertThrows(
            ViolationException.class,
            () -> curatorService.login(paramter));
    }

    @Test
    @DisplayName("가입하지 않는 이메일로 인한 로그인 실패")
    public void failToLogin2() throws NoSuchAlgorithmException {
        Curator curator = new Curator(
            "test@google.com",
            "홍길동",
            "test123!@"
        );
        curatorService.join(curator);

        LoginParamter paramter = new LoginParamter(
            "fff@google.com",
            "test445@#"
        );
        org.junit.jupiter.api.Assertions.assertThrows(
            ViolationException.class,
            () -> curatorService.login(paramter));
    }
}