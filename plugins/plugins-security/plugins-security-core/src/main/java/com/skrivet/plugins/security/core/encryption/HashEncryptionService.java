package com.skrivet.plugins.security.core.encryption;

import com.skrivet.core.common.exception.NotSupportExp;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

public class HashEncryptionService implements EncryptionService {
    private int hashIterations;
    private String algorithmName;

    public HashEncryptionService(String algorithmName, int hashIterations) {
        this.hashIterations = hashIterations;
        this.algorithmName = algorithmName;
    }


    @Override
    public String generateSalt() {
        return UUID.randomUUID().toString().replace("-", "").toUpperCase();
    }

    @Override
    public String encryptionPassword(String password, String salt) {
        try {
            return Hex.encodeToString(Hash.hash(algorithmName, password.getBytes("UTF-8"), salt.getBytes("UTF-8"), hashIterations));
        } catch (UnsupportedEncodingException e) {
            throw new NotSupportExp().variable( "utf-8");

        } catch (NoSuchAlgorithmException e) {
            throw new NotSupportExp().variable("algorithmName");

        }
    }
}
