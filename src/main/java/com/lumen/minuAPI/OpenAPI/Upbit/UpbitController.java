package com.lumen.minuAPI.OpenAPI.Upbit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/upbit")
public class UpbitController {

    @Autowired
    private UpbitService upbitService;

    @GetMapping
    public ResponseEntity<UpbitDTO> getUpbitData(
        @RequestParam(defaultValue = "minutes/15") String term,
        @RequestParam(defaultValue = "KRW") String transfrom,
        @RequestParam(defaultValue = "BTC") String transto,
        @RequestParam(defaultValue = "10") int count
    ) {
        return ResponseEntity.ok(
            upbitService.fetchUpbitData(
                term, transfrom, transto, count
            )
        );
    }

}
