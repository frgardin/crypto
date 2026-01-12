package org.gardin.felipe.crypto;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
}
