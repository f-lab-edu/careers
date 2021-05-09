package com.dev.careers.service.encryption;

public interface PasswordEncryptor {
    String makeSalt();
    String hashing(byte[] password, String salt);
}
