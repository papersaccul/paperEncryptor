package com.papersaccul.PaperEncryptor.util;
import java.util.HashMap;
import java.util.Map;

import com.papersaccul.PaperEncryptor.encryption.CaesarCipher;
import com.papersaccul.PaperEncryptor.encryption.EncryptionAlgorithm;
import com.papersaccul.PaperEncryptor.encryption.PaperCipher;
import com.papersaccul.PaperEncryptor.encryption.XORCipher;
import com.papersaccul.PaperEncryptor.encryption.asciiCipher;
import com.papersaccul.PaperEncryptor.encryption.base64Cipher;
import com.papersaccul.PaperEncryptor.encryption.binCipher;
import com.papersaccul.PaperEncryptor.encryption.md5Cipher;
import com.papersaccul.PaperEncryptor.encryption.hexCipher;
import com.papersaccul.PaperEncryptor.encryption.morseCipher;
public class AlgorithmLoader {
   private Map<String, Class<? extends EncryptionAlgorithm>> algorithms;
   public AlgorithmLoader() {
   algorithms = new HashMap<>();

   loadAlgorithms();
   }
   private void loadAlgorithms() {

   algorithms.put("Caesar Cipher", CaesarCipher.class);
   algorithms.put("XOR Cipher", XORCipher.class);
   algorithms.put("Paper Cipher", PaperCipher.class);
   algorithms.put("Bin Cipher", binCipher.class);
   algorithms.put("Base64", base64Cipher.class);
   algorithms.put("MD5", md5Cipher.class);
   algorithms.put("ASCII", asciiCipher.class);
   algorithms.put("Hex Cipher", hexCipher.class);
   algorithms.put("Morse Cipher", morseCipher.class);

   }
   public EncryptionAlgorithm getAlgorithm(String algorithmName) {
   try {
   Class<? extends EncryptionAlgorithm> algorithmClass = algorithms.get(algorithmName);
   if (algorithmClass != null) {
   return algorithmClass.getDeclaredConstructor().newInstance();
   }
   } catch (Exception e) {
   e.printStackTrace();
   }
   return null;
   }
}