package com.lumen.minuAPI.OpenAPI.Hash;

import org.springframework.stereotype.Service;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service
public class HashService {

    public String hash(String algorithm, String input) {
        try {
            MessageDigest md = MessageDigest.getInstance(algorithm);
            byte[] array = md.digest(input.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : array) {
                sb.append(String.format("%02X", b)); // 대문자 HEX
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            return "Error: Algorithm " + algorithm + " not found!";
        }
    }

    public String base64(String input, boolean encode) {
        try {
            if (encode) {
                return java.util.Base64.getEncoder().encodeToString(input.getBytes());
            }
            else {
                byte[] decodedBytes = java.util.Base64.getDecoder().decode(input);
                return new String(decodedBytes);
            }
        } catch (IllegalArgumentException e) {
            return "Error: Invalid Base64 input!";
        }
    }
}
