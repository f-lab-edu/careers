package com.dev.careers.service.encryption;

import com.dev.careers.service.error.NotSupportAlgorithmException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import org.springframework.stereotype.Component;

@Component
public class SHA256Encryptor implements PasswordEncryptor {

    private final static int SALT_SIZE = 16;

    @Override
    public String makeSalt() {
        SecureRandom srd = new SecureRandom();
        byte[] data = new byte[SALT_SIZE];
        srd.nextBytes(data);

        return byteArrayToString(data);
    }

    //Salt와 키 스트레칭 방식으로 구현
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

    public String byteArrayToString(byte[] bytes) {
        StringBuilder builder = new StringBuilder();
        for (byte data : bytes) {
            builder.append(String.format("%02X ", data));
        }
        return builder.toString();
    }
}
