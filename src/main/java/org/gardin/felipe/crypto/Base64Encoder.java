package org.gardin.felipe.crypto;

import java.util.Base64;

public final class Base64Encoder {

    public static byte[] decodeBytes(byte[] bytes) {
        return Base64.getDecoder().decode(bytes);
    }

    public static String encodeBytes(byte[] bytes) {
        return Base64.getEncoder().encodeToString(bytes);
    }

    private Base64Encoder() throws IllegalAccessException {
        throw new IllegalAccessException();
    }
}
