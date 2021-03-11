package com.dev.careers.service.encryption;

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
    public String hashing(byte[] password, String salt) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            builder.append(byteArrayToString(password));
            builder.append(salt);
            md.update(builder.toString().getBytes());
            password = md.digest();
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
