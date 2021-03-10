package com.dev.careers.service.encryption;

import java.security.NoSuchAlgorithmException;

public interface PasswordEncryption {
    String makeSalt();
    String hashing(byte[] password, String salt) throws NoSuchAlgorithmException;
}
