package com.dev.careers.util.encryption;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import org.springframework.stereotype.Component;

@Component
public class Sha256Encrypt{

    public String encrypt(String password, String salt) {
        String result = "";

        byte[] bytesSalt = salt.getBytes();
        byte[] pass = password.getBytes();
        byte[] bytes = new byte[pass.length + bytesSalt.length];

        try {
            // 암호화 방식 지정 메소드
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(bytes);

            byte[] byteData = md.digest();

            StringBuilder sb = new StringBuilder();
            for (byte byteDatum : byteData) {
                sb.append(Integer.toString((byteDatum & 0xFF) + 256, 16).substring(1));
            }

            result = sb.toString();
        } catch (NoSuchAlgorithmException e) {
           throw new RuntimeException(e);
        }

        return result;
    }

    public String generateSalt() {
        Random random = new Random();

        byte[] salt = new byte[8];
        random.nextBytes(salt);

        StringBuilder sb = new StringBuilder();
        for (byte b : salt) {
            // byte 값을 Hex 값으로 바꾸기.
            sb.append(String.format("%02x", b));
        }

        return sb.toString();
    }

}
