package com.dev.careers.service.encryption;

import com.dev.careers.service.error.NotSupportAlgorithmException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import org.springframework.stereotype.Component;

/**
 * SHA256을 사용한 비밀번호 암호화처리
 *
 * @author junehee
 */
@Component
public class Sha256Encryptor implements PasswordEncryptor {

    private static final int SALT_SIZE = 16;

    @Override
    public String makeSalt() {
        SecureRandom srd = new SecureRandom();
        byte[] data = new byte[SALT_SIZE];
        srd.nextBytes(data);

        return byteArrayToString(data);
    }

    /**
     * Salt와 키 스트레칭 방식으로 구현
     *
     * @param password 패스워드
     * @param salt 솔트키값
     * @return SHA256으로 암호화된 패스워드
     */
    @Override
    public String hashing(byte[] password, String salt) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");

            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < 10; i++) {
                builder.append(byteArrayToString(password));
                builder.append(salt);
                md.update(builder.toString().getBytes());
                password = md.digest();
            }
        } catch (NoSuchAlgorithmException ex) {
            throw new NotSupportAlgorithmException("사용할 수 없는 암호화 알고리즘 입니다.");
        }
        return new String(password);
    }

    /**
     *  byte 배열을 string으로 변환 메서드
     *
     * @param bytes String으로 변환할 byte array
     * @return 변환된 String
     */
    public String byteArrayToString(byte[] bytes) {
        StringBuilder builder = new StringBuilder();
        for (byte data : bytes) {
            builder.append(String.format("%02X ", data));
        }
        return builder.toString();
    }
}
