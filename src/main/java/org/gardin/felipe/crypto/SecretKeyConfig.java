package org.gardin.felipe.crypto;

import org.jspecify.annotations.NonNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Configuration
public class SecretKeyConfig {

    private static final String KEY_FILE_PATH = "static/secret.key";

    @Bean
    public SecretKey secretKey() throws Exception {
        Path keyPath = getKeyPath();
        return getSecretKey(keyPath);
    }

    private static @NonNull SecretKey getSecretKey(Path keyPath) throws IOException, NoSuchAlgorithmException {
        return Files.exists(keyPath) ?
                getExistentSecretKey(keyPath) :
                createANewSecretKey(keyPath);
    }

    private static @NonNull Path getKeyPath() {
        return Paths.get("src/main/resources/" + KEY_FILE_PATH);
    }

    private static @NonNull SecretKey createANewSecretKey(Path keyPath) throws NoSuchAlgorithmException, IOException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(128);
        SecretKey secretKey = keyGenerator.generateKey();
        byte[] encodedKey = secretKey.getEncoded();
        String base64Key = Base64.getEncoder().encodeToString(encodedKey);
        Files.createDirectories(keyPath.getParent());
        Files.write(keyPath, base64Key.getBytes());
        return secretKey;
    }

    private static @NonNull SecretKeySpec getExistentSecretKey(Path keyPath) throws IOException {
        byte[] encodedKey = Files.readAllBytes(keyPath);
        String base64Key = new String(encodedKey).trim();
        byte[] decodedKey = Base64.getDecoder().decode(base64Key);
        return new SecretKeySpec(decodedKey, "AES");
    }
}
