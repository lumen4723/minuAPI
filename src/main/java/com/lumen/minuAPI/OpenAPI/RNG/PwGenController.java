package com.lumen.minuAPI.OpenAPI.RNG;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pwgen")
public class PwGenController {
    
    @Autowired
    private PwGenService pwGenService;

    @GetMapping
    public ResponseEntity<String> PasswordGenerator(
        @RequestParam(defaultValue = "10") int length,
        @RequestParam(defaultValue = "true") boolean upper,
        @RequestParam(defaultValue = "true") boolean lower,
        @RequestParam(defaultValue = "true") boolean number,
        @RequestParam(defaultValue = "false") boolean special
    ) {
        return ResponseEntity.ok(
            pwGenService.generatePassword(
                length, upper, lower, number, special
            )
        );
    }
}
