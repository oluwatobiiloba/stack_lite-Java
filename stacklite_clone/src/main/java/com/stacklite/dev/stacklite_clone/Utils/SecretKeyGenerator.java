package com.stacklite.dev.stacklite_clone.Utils;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class SecretKeyGenerator {

    public static void main(String[] args) throws NoSuchAlgorithmException {

        SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

        String encodedKey = encodeSecretKey(key);

        System.out.println("Secret Key: " + encodedKey);
    }

    private static String encodeSecretKey(SecretKey secretKey) {
        byte[] keyBytes = secretKey.getEncoded();
        return Base64.getEncoder().encodeToString(keyBytes);
    }

}
