package com.papersaccul.PaperEncryptor.encryption;

import java.nio.charset.Charset;

public class binCipher implements EncryptionAlgorithm {
    @Override
    public String encrypt(String text, String key, String charset, String... additionalParams) {
        byte[] bytes = text.getBytes(Charset.forName(charset));
        StringBuilder binary = new StringBuilder();
        for (byte b : bytes) {
            int val = b;
            for (int i = 0; i < 8; i++) {
                binary.append((val & 128) == 0 ? 0 : 1);
                val <<= 1;
            }
            binary.append(' ');
        }
        return binary.toString();
    }

    @Override
    public String decrypt(String text, String key, String charset, String... additionalParams) {
        String[] binaryBlocks = text.split(" ");
        StringBuilder sb = new StringBuilder();
        for (String block : binaryBlocks) {
            sb.append((char) Integer.parseInt(block, 2));
        }
        return new String(sb.toString().getBytes(Charset.forName(charset)), Charset.forName(charset));
    }
}
