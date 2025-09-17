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

    @GetMapping("/md5/{input}")
    public String md5(@PathVariable String input) {
        return hashService.hash("MD5", input);
    }

    @GetMapping("/sha1/{input}")
    public String sha1(@PathVariable String input) {
        return hashService.hash("SHA-1", input);
    }

    @GetMapping("/sha224/{input}")
    public String sha224(@PathVariable String input) {
        return hashService.hash("SHA-224", input);
    }

    @GetMapping("/sha256/{input}")
    public String sha256(@PathVariable String input) {
        return hashService.hash("SHA-256", input);
    }

    @GetMapping("/sha384/{input}")
    public String sha384(@PathVariable String input) {
        return hashService.hash("SHA-384", input);
    }

    @GetMapping("/sha512/{input}")
    public String sha512(@PathVariable String input) {
        return hashService.hash("SHA-512", input);
    }

    @GetMapping("/sha3-224/{input}")
    public String sha3_224(@PathVariable String input) {
        return hashService.hash("SHA3-224", input);
    }

    @GetMapping("/sha3-256/{input}")
    public String sha3_256(@PathVariable String input) {
        return hashService.hash("SHA3-256", input);
    }

    @GetMapping("/sha3-384/{input}")
    public String sha3_384(@PathVariable String input) {
        return hashService.hash("SHA3-384", input);
    }

    @GetMapping("/sha3-512/{input}")
    public String sha3_512(@PathVariable String input) {
        return hashService.hash("SHA3-512", input);
    }
}
