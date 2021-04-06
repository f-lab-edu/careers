package com.dev.careers.service;

import com.dev.careers.model.Curator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DuplicateKeyException;
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
            DuplicateKeyException.class,
            () -> curatorService.join(curator));
    }
}