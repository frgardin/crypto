package org.gardin.felipe.crypto;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class CryptoController {

    private final CryptoService cryptoService;

    public CryptoController(CryptoService cryptoService) {
        this.cryptoService = cryptoService;
    }

    @PostMapping(ResourcePath.ENCRYPT_PATH)
    public HttpEntity<CryptoDTO> encrypt(@RequestBody CryptoDTO cryptoDTO) throws Exception {
        return ResponseEntity.ok(cryptoService.encrypt(cryptoDTO));
    }

    @PostMapping(ResourcePath.DECRYPT_PATH)
    public HttpEntity<CryptoDTO> decrypt(@RequestBody CryptoDTO cryptoDTO) throws Exception {
        return ResponseEntity.ok(cryptoService.decrypt(cryptoDTO));
    }

    @PostMapping(ResourcePath.HASH_PATH)
    public HttpEntity<CryptoDTO> hash(@RequestBody CryptoDTO cryptoDTO) throws Exception {
        return ResponseEntity.ok(cryptoService.hash(cryptoDTO));
    }

    @PostMapping("/api/encrypt-file")
    public ResponseEntity<byte[]> encryptFile(@RequestParam("file") MultipartFile file) throws Exception {
        byte[] encryptedData = cryptoService.encryptFile(file);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"encrypted_" + file.getOriginalFilename() + "\"")
                .body(encryptedData);
    }

    @PostMapping("/api/decrypt-file")
    public ResponseEntity<byte[]> decryptFile(@RequestParam("file") MultipartFile file) throws Exception {
        byte[] decryptedData = cryptoService.decryptFile(file);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"decrypted_" + file.getOriginalFilename() + "\"")
                .body(decryptedData);
    }
}
