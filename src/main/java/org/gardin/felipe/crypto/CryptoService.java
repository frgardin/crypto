package org.gardin.felipe.crypto;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import java.security.MessageDigest;
import java.util.Base64;
import java.util.HexFormat;

@Service
public class CryptoService {

    private static final String AES_TRANSFORMATION = "AES";
    private static final String SHA_256_ALGORITHM = "SHA-256";

    private final SecretKey key;

    public CryptoService(SecretKey key) {
        this.key = key;
    }

    public CryptoDTO encrypt(CryptoDTO cryptoDTO) throws Exception {
        Cipher cipher = createCipher(Cipher.ENCRYPT_MODE);
        byte[] cipherText = cipher.doFinal(cryptoDTO.text().getBytes());
        return CryptoDTO.from(Base64Serializer.encodeBytes(cipherText));
    }

    public CryptoDTO decrypt(CryptoDTO cryptoDTO) throws Exception {
        Cipher cipher = createCipher(Cipher.DECRYPT_MODE);
        byte[] cryptoBytes = Base64Serializer.decodeBytes(cryptoDTO.text().getBytes());
        byte[] decryptedBytes = cipher.doFinal(cryptoBytes);
        return CryptoDTO.from(new String(decryptedBytes));
    }

    public CryptoDTO hash(CryptoDTO cryptoDTO) throws Exception {
        MessageDigest digest = MessageDigest.getInstance(SHA_256_ALGORITHM);
        byte[] hashBytes = digest.digest(cryptoDTO.text().getBytes());
        return CryptoDTO.from(HexFormat.of().formatHex(hashBytes));
    }

    public byte[] encryptFile(MultipartFile file) throws Exception {
        Cipher cipher = createCipher(Cipher.ENCRYPT_MODE);
        return cipher.doFinal(file.getBytes());
    }

    public byte[] decryptFile(MultipartFile file) throws Exception {
        Cipher cipher = createCipher(Cipher.DECRYPT_MODE);
        return cipher.doFinal(file.getBytes());
    }

    private Cipher createCipher(int mode) throws Exception {
        Cipher cipher = Cipher.getInstance(AES_TRANSFORMATION);
        cipher.init(mode, key);
        return cipher;
    }
}
