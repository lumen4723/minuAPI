package com.lumen.minuAPI.OpenAPI.RNG;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class PwGenController {
    
    @GetMapping("/pwgen")
    public ResponseEntity<String> PasswordGenerator(
        @RequestParam(defaultValue = "10") int length,
        @RequestParam(defaultValue = "true") boolean upper,
        @RequestParam(defaultValue = "true") boolean lower,
        @RequestParam(defaultValue = "true") boolean number,
        @RequestParam(defaultValue = "false") boolean special
    ) {
        return ResponseEntity.ok(
            PwGenService.generatePassword(
                length, upper, lower, number, special
            )
        );
    }
    
}
