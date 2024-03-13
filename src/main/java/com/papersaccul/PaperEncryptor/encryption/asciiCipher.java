package com.papersaccul.PaperEncryptor.encryption;

public class asciiCipher implements EncryptionAlgorithm {
    @Override
    public String encrypt(String text, String key, String charset, String... additionalParams) {
        StringBuilder sb = new StringBuilder();
        for (char c : text.toCharArray()) {
            if (sb.length() > 0) sb.append(" ");
            sb.append((int) c);
        }
        return sb.toString();
    }

    @Override
    public String decrypt(String text, String key, String charset, String... additionalParams) {
        StringBuilder sb = new StringBuilder();
        String[] asciiCodes = text.split(" ");
        for (String code : asciiCodes) {
            sb.append((char) Integer.parseInt(code));
        }
        return sb.toString();
    }
}
