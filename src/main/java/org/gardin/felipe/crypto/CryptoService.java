package org.gardin.felipe.crypto;

import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import java.security.MessageDigest;
import java.util.Base64;

@Service
public class CryptoService {

    private final SecretKey key;

    public CryptoService(SecretKey key) {
        this.key = key;
    }

    public String encrypt(String text) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] cypherText = cipher.doFinal(text.getBytes());
        return Base64.getEncoder().encodeToString(cypherText);
    }

    public String decrypt(String encryptedText) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] cryptoByte = Base64.getDecoder().decode(encryptedText.getBytes());
        byte[] byteText = cipher.doFinal(cryptoByte);
        return new String(byteText);
    }

    public String hash(String text) throws Exception {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hashBytes = digest.digest(text.getBytes());
        return java.util.HexFormat.of().formatHex(hashBytes);
    }
}
