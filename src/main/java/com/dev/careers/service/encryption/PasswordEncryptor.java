package com.dev.careers.service.encryption;

/**
 * 비밀번호 암호화처리를 언제든 다른 방식으로 변경 가능하도록 인터페이스 처리
 *
 * @author junehee
 */
public interface PasswordEncryptor {

    String makeSalt();

    String hashing(byte[] password, String salt);
}
