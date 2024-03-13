package com.papersaccul.PaperEncryptor.encryption;

import java.nio.charset.Charset;

public class RailFenceCipher implements EncryptionAlgorithm {
    private static final String DEFAULT_KEY = "1";

    @Override
    public String encrypt(String text, String key, String charset, String... additionalParams) {
        if (key == null || key.isEmpty()) {
            key = DEFAULT_KEY;
        }
        int depth = Integer.parseInt(key);
        if (depth <= 1) return text;

        StringBuilder[] rows = new StringBuilder[depth];
        for (int i = 0; i < depth; i++) {
            rows[i] = new StringBuilder();
        }

        int row = 0;
        boolean down = true;
        for (char ch : text.toCharArray()) {
            rows[row].append(ch);
            if (row == depth - 1) {
                down = false;
            } else if (row == 0) {
                down = true;
            }
            row += down ? 1 : -1;
        }

        StringBuilder encryptedText = new StringBuilder();
        for (StringBuilder sb : rows) {
            encryptedText.append(sb);
        }

        return new String(encryptedText.toString().getBytes(Charset.forName(charset)), Charset.forName(charset));
    }

    @Override
    public String decrypt(String text, String key, String charset, String... additionalParams) {
        if (key == null || key.isEmpty()) {
            key = DEFAULT_KEY;
        }
        int depth = Integer.parseInt(key);
        if (depth <= 1) return text;

        char[] decrypted = new char[text.length()];
        int n = 0;
        for (int k = 0; k < depth; k++) {
            int index = k;
            boolean down = true;
            while (index < text.length()) {
                decrypted[index] = text.charAt(n++);
                if (k == 0 || k == depth - 1) {
                    index += 2 * (depth - 1);
                } else {
                    if (down) {
                        index += 2 * (depth - k - 1);
                    } else {
                        index += 2 * k;
                    }
                    down = !down;
                }
            }
        }

        return new String(decrypted);
    }
}