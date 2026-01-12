package org.gardin.felipe.crypto;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CryptoController {

    private final CryptoService cryptoService;

    public CryptoController(CryptoService cryptoService) {
        this.cryptoService = cryptoService;
    }

    @GetMapping(ResourcePath.ENCRYPT_PATH)
    public String encrypt(@RequestParam String text) throws Exception {
        return cryptoService.encrypt(text);
    }

    @GetMapping(ResourcePath.DECRYPT_PATH)
    public String decrypt(@RequestParam String text) throws Exception {
        return cryptoService.decrypt(text);
    }

    @GetMapping(ResourcePath.HASH_PATH)
    public String hash(@RequestParam String text) throws Exception {
        return cryptoService.hash(text);
    }
}
