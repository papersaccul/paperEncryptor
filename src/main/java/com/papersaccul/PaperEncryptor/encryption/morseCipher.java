package com.papersaccul.PaperEncryptor.encryption;

import java.util.HashMap;
import java.util.Map;

public class morseCipher implements EncryptionAlgorithm {
    private static final Map<Character, String> charToMorse = new HashMap<>();
    static {
        charToMorse.put('A', ".-");
        charToMorse.put('B', "-...");
        charToMorse.put('C', "-.-.");
        charToMorse.put('D', "-..");
        charToMorse.put('E', ".");
        charToMorse.put('F', "..-.");
        charToMorse.put('G', "--.");
        charToMorse.put('H', "....");
        charToMorse.put('I', "..");
        charToMorse.put('J', ".---");
        charToMorse.put('K', "-.-");
        charToMorse.put('L', ".-..");
        charToMorse.put('M', "--");
        charToMorse.put('N', "-.");
        charToMorse.put('O', "---");
        charToMorse.put('P', ".--.");
        charToMorse.put('Q', "--.-");
        charToMorse.put('R', ".-.");
        charToMorse.put('S', "...");
        charToMorse.put('T', "-");
        charToMorse.put('U', "..-");
        charToMorse.put('V', "...-");
        charToMorse.put('W', ".--");
        charToMorse.put('X', "-..-");
        charToMorse.put('Y', "-.--");
        charToMorse.put('Z', "--..");
    }

    @Override
    public String encrypt(String text, String key, String charset, String... additionalParams) {
        StringBuilder sb = new StringBuilder();
        text = text.toUpperCase();
        for (char c : text.toCharArray()) {
            if (charToMorse.containsKey(c)) {
                sb.append(charToMorse.get(c)).append(" ");
            } else {
                sb.append(c).append(" ");
            }
        }
        return sb.toString().trim();
    }

    @Override
    public String decrypt(String text, String key, String charset, String... additionalParams) {
        StringBuilder sb = new StringBuilder();
        String[] morseCodes = text.split(" ");
        for (String code : morseCodes) {
            for (Map.Entry<Character, String> entry : charToMorse.entrySet()) {
                if (entry.getValue().equals(code)) {
                    sb.append(entry.getKey());
                    break;
                }
            }
        }
        return sb.toString();
    }
}
