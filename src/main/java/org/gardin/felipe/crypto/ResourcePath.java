package org.gardin.felipe.crypto;

public final class ResourcePath {

    private static final String API = "/api";
    private static final String FILE = "-file";

    public static final String DECRYPT_PATH = API + "/decrypt";
    public static final String DECRYPT_FILE_PATH = DECRYPT_PATH + FILE;
    public static final String ENCRYPT_PATH = API + "/encrypt";
    public static final String ENCRYPT_PATH_FILE_PATH = ENCRYPT_PATH + FILE;
    public static final String HASH_PATH = API + "/hash";

    public ResourcePath() throws IllegalAccessException {
        throw new IllegalAccessException();
    }
}
