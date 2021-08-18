package com.skrivet.plugins.security.core.encryption;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Hash {
    public static byte[] hash(String algorithmName,byte[] bytes, byte[] salt, int hashIterations) throws NoSuchAlgorithmException {
        MessageDigest digest =  MessageDigest.getInstance(algorithmName);
        if (salt != null) {
            digest.reset();
            digest.update(salt);
        }

        byte[] hashed = digest.digest(bytes);
        int iterations = hashIterations - 1;

        for (int i = 0; i < iterations; ++i) {
            digest.reset();
            hashed = digest.digest(hashed);
        }

        return hashed;
    }



}
