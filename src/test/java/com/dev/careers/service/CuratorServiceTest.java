package com.dev.careers.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.dev.careers.mapper.CuratorMapper;
import com.dev.careers.model.Curator;
import com.dev.careers.model.LoginParamter;
import com.dev.careers.service.encryption.PasswordEncryptor;
import com.dev.careers.service.error.DuplicatedEmailException;
import com.dev.careers.service.error.ViolationException;
import org.junit.Ignore;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CuratorServiceTest {

    CuratorService curatorService;

    @Mock
    CuratorMapper curatorMapper;

    @Mock
    PasswordEncryptor passwordEncryptor;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        curatorService = new CuratorService(this.curatorMapper, this.passwordEncryptor);
    }

    @AfterEach
    public void close() throws Exception {
        MockitoAnnotations.openMocks(this).close();
    }

    @Test
    @Ignore
    @DisplayName("회원가입된 정보로 로그인 요청 시 로그인 성공한다.")
    public void successLogin() {
        String email = "test@google.com";
        String password = "test123!@";
        String salt = "HMFZ0PRB8rdt3LojrBjtug==";
        String encryptedPassword =
            "9fca72f08da46ade3aab40a9aalfe7f9f2374fcbc91994a09335c99074723d";
        LoginParamter loginParamter = new LoginParamter(email, password);
        Curator memberInfo = new Curator(1, email, "홍길동", encryptedPassword, salt);

        given(curatorMapper.getMemberInfo(loginParamter.getEmail())).willReturn(memberInfo);

        given(passwordEncryptor.hashing(loginParamter.getPassword().getBytes(), salt))
            .willReturn(encryptedPassword);

        int result = curatorService.getUserIdByEmailAndPassword(loginParamter);

        assertThat(result, is(1));
    }
    
    @Test
    @DisplayName("정상적인 회원가입 처리 로직 수행")
    public void join_VaildData_True() {
        String email = "test@google.com";
        String password = "test123!@";
        String salt = "HMFZ0PRB8rdt3LojrBjtug==";
        String encryptedPassword =
            "9fca72f08da46ade3aab40a9aalfe7f9f2374fcbc91994a09335c99074723d";
        Curator curator = new Curator(email, "홍길동", password);

        given(curatorMapper.checkEmailExists(email)).willReturn(false);
        given(passwordEncryptor.makeSalt()).willReturn(salt);
        given(curatorMapper.insertCurator(curator)).willReturn(1);
        given(passwordEncryptor.hashing(password.getBytes(), salt))
            .willReturn(encryptedPassword);

       curatorService.join(curator);
    }

    @Test
    @DisplayName("중복된 이메일 회원가입 요청 시 DuplicatedEmailException 예외가 발생한다.")
    public void dupicatedEmail() {
        String email = "test@google.com";
        String password = "test123!@";
        Curator curator = new Curator(email, "홍길동", password);

        given(curatorMapper.checkEmailExists(curator.getEmail())).willReturn(true);

        DuplicatedEmailException exception = assertThrows(DuplicatedEmailException.class,
            () -> curatorService.join(curator));

        String message = exception.getMessage();

        assertThat("이미 가입된 이메일 입니다.", is(message));

        verify(curatorMapper, times(1)).checkEmailExists(anyString());
    }

    @Test
    @DisplayName("회원가입된 정보로 로그인 하지 않았을 경우 ViolationException 예외가 발생한다.")
    public void passwordMismatchAtLogin() {
        String email = "test@google.com";
        String incorrectPassword = "test123!#";
        String salt = "HMFZ0PRB8rdt3LojrBjtug==";
        String encryptedPassword =
            "9fca72f08da46ade3aab40a9aalfe7f9f2374fcbc91994a09335c99074723d";
        String incorrectEncryptedPassword =
            "9fca72f08da46ade3aab40a9aalfe7f9f2374fcbc91994a09335c99074723b";
        LoginParamter loginParamter = new LoginParamter(email, incorrectPassword);
        Curator curator = new Curator(1, email, "홍길동", encryptedPassword, salt);

        given(curatorMapper.getMemberInfo(loginParamter.getEmail())).willReturn(curator);
        given(passwordEncryptor.hashing(incorrectPassword.getBytes(), salt))
            .willReturn(incorrectEncryptedPassword);

        ViolationException exception = assertThrows(ViolationException.class,
            () -> curatorService.getUserIdByEmailAndPassword(loginParamter));

        String message = exception.getMessage();

        assertThat("등록되지 않은 회원입니다.", is(message));
    }

    @Test
    @DisplayName("가입하지 않는 이메일로 인한 로그인 요청 시 ViolationException 예외 발생한다.")
    public void unsubscribedLoginAttempt() {
        String email = "test123@google.com";
        String password = "test123!@";
        LoginParamter loginParamter = new LoginParamter(email, password);

        given(curatorMapper.getMemberInfo(loginParamter.getEmail()))
            .willThrow(new ViolationException("등록되지 않은 회원입니다."));

        ViolationException exception = assertThrows(ViolationException.class,
            () -> curatorService.getUserIdByEmailAndPassword(loginParamter));

        String message = exception.getMessage();

        assertThat("등록되지 않은 회원입니다.", is(message));

    }
}