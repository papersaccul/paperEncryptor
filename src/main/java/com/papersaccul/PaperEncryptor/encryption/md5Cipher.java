package com.papersaccul.PaperEncryptor.encryption;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class md5Cipher implements EncryptionAlgorithm {
    @Override
    public String encrypt(String text, String key, String charset, String... additionalParams) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(text.getBytes());
            byte[] digest = md.digest();
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("erorr MD5", e);
        }
    }

    @Override
    public String decrypt(String text, String key, String charset, String... additionalParams) {
        throw new UnsupportedOperationException("MD5 cannot be decrypted");
    }
}


