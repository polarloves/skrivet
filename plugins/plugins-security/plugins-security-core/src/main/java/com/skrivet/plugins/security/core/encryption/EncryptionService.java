package com.skrivet.plugins.security.core.encryption;

public interface EncryptionService {
    public String generateSalt();
    public String encryptionPassword(String password, String salt);
}
