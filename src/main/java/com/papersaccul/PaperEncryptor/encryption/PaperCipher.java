package com.papersaccul.PaperEncryptor.encryption;
import java.nio.charset.Charset;

public class PaperCipher implements EncryptionAlgorithm {
    private static final String DEFAULT_KEY = "paper";
    private static final String BASE64_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";

    @Override
    public String encrypt(String text, String key, String charset, String... additionalParams) {
        if (key == null || key.isEmpty()) {
            key = DEFAULT_KEY;
        }
        Charset encodingCharset = Charset.forName(charset);
        // semi-base64
        StringBuilder base64like = new StringBuilder();
        int val = 0;
        int valBit = 0;
        byte[] textBytes = text.getBytes(encodingCharset);
        for (byte b : textBytes) {
            val = (val << 8) + (b & 0xFF);
            valBit += 8;
            while (valBit >= 6) {
                char base64Char = BASE64_CHARS.charAt((val >> (valBit - 6)) & 63); // Извлекаем 6 бит
                base64like.append(base64Char);
                valBit -= 6;
            }
        }
        if (valBit > 0) {
            base64like.append(BASE64_CHARS.charAt((val << (6 - valBit)) & 63));
        }
        
        // XOR
        byte[] keyBytes = key.getBytes(encodingCharset);
        StringBuilder encrypted = new StringBuilder();
        for (int i = 0; i < base64like.length(); i++) {
            char xorChar = (char) (base64like.charAt(i) ^ keyBytes[i % keyBytes.length]);
            encrypted.append(xorChar);
        }
        
        return new String(encrypted.toString().getBytes(), encodingCharset);
    }

    @Override
    public String decrypt(String text, String key, String charset, String... additionalParams) {
        if (key == null || key.isEmpty()) {
            key = DEFAULT_KEY;
        }
        Charset encodingCharset = Charset.forName(charset);
        // XOR
        byte[] keyBytes = key.getBytes(encodingCharset);
        StringBuilder temp = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            char xorChar = (char) (text.charAt(i) ^ keyBytes[i % keyBytes.length]);
            temp.append(xorChar);
        }
        
        // semi-base64
        StringBuilder decrypted = new StringBuilder();
        int val = 0;
        int valBit = 0;
        for (char character : temp.toString().toCharArray()) {
            int charVal = BASE64_CHARS.indexOf(character);
            if (charVal == -1) continue;
            val = (val << 6) + charVal;
            valBit += 6;
            while (valBit >= 8) {
                decrypted.append((char) ((val >> (valBit - 8)) & 255));
                valBit -= 8;
            }
        }
        
        return new String(decrypted.toString().getBytes(), encodingCharset);
    }
}
