package com.papersaccul.PaperEncryptor.encryption;

public interface EncryptionAlgorithm {
    String encrypt(String text, String key, String charset, String... additionalParams);
    String decrypt(String text, String key, String charset, String... additionalParams);
}
