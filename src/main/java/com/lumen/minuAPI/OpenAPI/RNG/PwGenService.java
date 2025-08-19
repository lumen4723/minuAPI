package com.lumen.minuAPI.OpenAPI.RNG;

import org.springframework.stereotype.Service;

@Service
public class PwGenService {
    
    public static String generatePassword(
        int length, boolean upper, boolean lower, boolean number, boolean special
    ) {
        // length 를 1보다 크게 256보다는 작게 설정
        length = Math.min(256, Math.max(1, length));

        StringBuilder password = new StringBuilder();
        String upperChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lowerChars = "abcdefghijklmnopqrstuvwxyz";
        String numberChars = "0123456789";
        String specialChars = "!@#$%^&*()_+-=[]{}|;:,.<>?";
        
        String chars = "";
        if (upper) { chars += upperChars; }
        if (lower) { chars += lowerChars; }
        if (number) { chars += numberChars; }
        if (special) { chars += specialChars; }
        
        for (int i = 0; i < length; i++) {
            int random = (int) (Math.random() * chars.length());
            password.append(chars.charAt(random));
        }
        
        return password.toString();
    }

}
