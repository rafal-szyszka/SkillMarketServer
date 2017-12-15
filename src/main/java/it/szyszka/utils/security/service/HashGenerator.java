package it.szyszka.utils.security.service;

import com.google.common.hash.Hashing;

import java.nio.charset.StandardCharsets;

/**
 * Created by rafal on 28.09.17.
 */
public class HashGenerator {

    public static String generateSHA256Key(String rawData) {
        return Hashing.sha256()
                .hashString(rawData, StandardCharsets.UTF_8)
                .toString();
    }

    public static String generateSHA512Key(String rawData) {
        return Hashing.sha512()
                .hashString(rawData, StandardCharsets.UTF_8)
                .toString();
    }

}
