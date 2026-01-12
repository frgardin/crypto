package org.gardin.felipe.crypto;

import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import java.security.MessageDigest;
import java.util.Base64;
import java.util.HexFormat;

@Service
public class CryptoService {

    private final SecretKey key;

    public CryptoService(SecretKey key) {
        this.key = key;
    }

    public CryptoDTO encrypt(CryptoDTO cryptoDTO) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] cypherText = cipher.doFinal(cryptoDTO.text().getBytes());
        return CryptoDTO.from(Base64.getEncoder().encodeToString(cypherText));
    }

    public CryptoDTO decrypt(CryptoDTO cryptoDTO) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] cryptoByte = Base64.getDecoder().decode(cryptoDTO.text().getBytes());
        byte[] byteText = cipher.doFinal(cryptoByte);
        return CryptoDTO.from(new String(byteText));
    }

    public CryptoDTO hash(CryptoDTO cryptoDTO) throws Exception {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hashBytes = digest.digest(cryptoDTO.text().getBytes());
        return CryptoDTO.from(HexFormat.of().formatHex(hashBytes));
    }
}
