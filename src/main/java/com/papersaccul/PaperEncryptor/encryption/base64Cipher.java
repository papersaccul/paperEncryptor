package com.papersaccul.PaperEncryptor.encryption;

import java.nio.charset.Charset;
import java.util.Base64;

public class base64Cipher implements EncryptionAlgorithm {

    @Override
    public String encrypt(String text, String key, String charset, String... additionalParams) {
        return new String(Base64.getEncoder().encode(text.getBytes(Charset.forName(charset))), Charset.forName(charset));
    }

    @Override
    public String decrypt(String text, String key, String charset, String... additionalParams) {
        return new String(Base64.getDecoder().decode(text), Charset.forName(charset));
    }
}
