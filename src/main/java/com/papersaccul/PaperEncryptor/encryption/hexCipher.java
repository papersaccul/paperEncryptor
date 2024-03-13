package com.papersaccul.PaperEncryptor.encryption;

public class hexCipher implements EncryptionAlgorithm {
    @Override
    public String encrypt(String text, String key, String charset, String... additionalParams) {
        StringBuilder sb = new StringBuilder();
        for (char c : text.toCharArray()) {
            sb.append(String.format("%02X", (int) c));
        }
        return sb.toString();
    }

    @Override
    public String decrypt(String text, String key, String charset, String... additionalParams) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < text.length(); i += 2) {
            String str = text.substring(i, i + 2);
            sb.append((char) Integer.parseInt(str, 16));
        }
        return sb.toString();
    }
}
