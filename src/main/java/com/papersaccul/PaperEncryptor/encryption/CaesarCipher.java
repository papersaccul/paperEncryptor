package com.papersaccul.PaperEncryptor.encryption;

import java.nio.charset.Charset;

public class CaesarCipher implements EncryptionAlgorithm {
    private static final String DEFAULT_KEY = "1";

    @Override
    public String encrypt(String text, String key, String charset, String... additionalParams) {
        if (key == null || key.isEmpty()) {
            key = DEFAULT_KEY;
        }
        int shift = Integer.parseInt(key);
        StringBuilder result = new StringBuilder();

        for (char c : text.toCharArray()) {
            if (Character.isLetter(c)) {
                int originalAlphabetPosition = c;
                int shiftedPosition = originalAlphabetPosition + shift;
                char shiftedChar = (char) shiftedPosition;
                result.append(shiftedChar);
            } else {
                result.append(c);
            }
        }

        return new String(result.toString().getBytes(Charset.forName(charset)), Charset.forName(charset));
    }

    @Override
    public String decrypt(String text, String key, String charset, String... additionalParams) {
        if (key == null || key.isEmpty()) {
            key = DEFAULT_KEY;
        }
        int shift = Integer.parseInt(key);
        StringBuilder result = new StringBuilder();

        for (char c : text.toCharArray()) {
            if (Character.isLetter(c)) {
                int originalAlphabetPosition = c;
                int shiftedPosition = originalAlphabetPosition - shift;
                char shiftedChar = (char) shiftedPosition;
                result.append(shiftedChar);
            } else {
                result.append(c);
            }
        }

        return new String(result.toString().getBytes(Charset.forName(charset)), Charset.forName(charset));
    }
}
