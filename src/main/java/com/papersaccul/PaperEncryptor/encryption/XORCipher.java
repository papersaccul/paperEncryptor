package com.papersaccul.PaperEncryptor.encryption;

import java.nio.charset.Charset;

public class XORCipher implements EncryptionAlgorithm {
    private static final String DEFAULT_KEY = "default";

    @Override
    public String encrypt(String text, String key, String charset, String... additionalParams) {
        if (key == null || key.isEmpty()) {
            key = DEFAULT_KEY;
        }
        byte[] textBytes = text.getBytes(Charset.forName(charset));
        byte[] keyBytes = key.getBytes(Charset.forName(charset));
        byte[] encryptedBytes = new byte[textBytes.length];
        for (int i = 0; i < textBytes.length; i++) {
            encryptedBytes[i] = (byte) (textBytes[i] ^ keyBytes[i % keyBytes.length]);
        }
        return new String(encryptedBytes, Charset.forName(charset));
    }
    @Override
    public String decrypt(String text, String key, String charset, String... additionalParams) {
        return encrypt(text, key, charset);
    }
}
