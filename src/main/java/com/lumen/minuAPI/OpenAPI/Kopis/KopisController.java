package com.lumen.minuAPI.OpenAPI.Kopis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/kopis")
public class KopisController {

    @Value("${api-key.kopis}")
    private String apiKey;

    @Autowired
    private KopisService kopisService;

    @GetMapping
    public ResponseEntity<KopisDTO> getKopisData(
        @RequestParam String start,
        @RequestParam String end,
        @RequestParam(defaultValue = "10") int row,
        @RequestParam(defaultValue = "1") int page,
        @RequestParam(required = false) String state,
        @RequestParam(required = false) String localnum
    ) {
        return ResponseEntity.ok(
            kopisService.fetchKopisData(
                apiKey, start, end, row, page, state, localnum
            )
        );
    }
}
