package com.example.onlinebank.security_service;

import lombok.SneakyThrows;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;

public class EncryptService {

    public static final String ALGORITHM = "AES";
    public static final int KEY_SIZE = 128;

    @SneakyThrows
    public byte[] encrypt(byte[] data, SecretKey key) {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        return cipher.doFinal(data);
    }

    @SneakyThrows
    public SecretKey getSecretKey() {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(ALGORITHM);
        keyGenerator.init(KEY_SIZE);
        return keyGenerator.generateKey();
    }
}
