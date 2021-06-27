package com.dev.careers.service;

import com.dev.careers.model.Curator;
import com.dev.careers.model.LoginParamter;
import com.dev.careers.service.error.DuplicatedEmailException;
import com.dev.careers.service.error.ViolationException;
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
    @DisplayName("중복된 이메일 회원가입 요청 시 DuplicatedEmailException 예외가 발생한다.")
    public void dupicatedEmail() {
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
    @DisplayName("회원가입된 정보로 로그인 요청 시 로그인 성공한다.")
    public void successLogin() {
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
            () -> curatorService.getUserIdByEmailAndPassword(paramter));
    }

    @Test
    @DisplayName("회원가입된 정보로 로그인 하지 않았을 경우 ViolationException 예외가 발생한다.")
    public void passwordMismatchAtLogin() {
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
            () -> curatorService.getUserIdByEmailAndPassword(paramter));
    }

    @Test
    @DisplayName("가입하지 않는 이메일로 인한 로그인 요청 시 ViolationException 예외 발생한다.")
    public void unsubscribedLoginAttempt() {
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
            () -> curatorService.getUserIdByEmailAndPassword(paramter));
    }
}