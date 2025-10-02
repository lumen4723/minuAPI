package com.lumen.minuAPI.OpenAPI.Hash;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HashController {

    private final HashService hashService;

    public HashController(HashService hashService) {
        this.hashService = hashService;
    }

    @GetMapping("/{algorithm}/{input}")
    public String hash(@PathVariable String algorithm, @PathVariable String input) {
        // 지원하는 알고리즘 목록
        String[] supported = {"MD5", "SHA-1", "SHA-224", "SHA-256", "SHA-384", "SHA-512", "SHA3-224", "SHA3-256", "SHA3-384", "SHA3-512"};
        boolean isSupported = java.util.Arrays.asList(supported).contains(algorithm.toUpperCase());
        if (!isSupported) {
            return "지원하지 않는 해시 알고리즘입니다.";
        }
        return hashService.hash(algorithm.toUpperCase(), input);
    }

    @GetMapping("/base64/{action}/{input}")
    public String base64(@PathVariable String action, @PathVariable String input) {
        if ("encode".equalsIgnoreCase(action)) {
            return hashService.base64(input, true);
        }
        else if ("decode".equalsIgnoreCase(action)) {
            return hashService.base64(input, false);
        }
        else {
            return "지원하지 않는 base64 액션입니다. (encode/decode만 가능)";
        }
    }

}
