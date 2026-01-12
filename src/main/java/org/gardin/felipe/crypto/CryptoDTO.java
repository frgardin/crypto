package org.gardin.felipe.crypto;

public record CryptoDTO(
        String text
) {

    public static CryptoDTO from(String text) {
        return new CryptoDTO(text);
    }
}
