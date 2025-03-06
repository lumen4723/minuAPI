package com.lumen.minuAPI.OpenAPI.Kma;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/kma")
public class KmaController {
    
    @Value("${api-key.kma}")
    private String apiKey;

    @Autowired
    private KmaService kmaService;

    @GetMapping
    public ResponseEntity<KmaDTO> getKmaData(
            @RequestParam(required = false) String ymd,
            @RequestParam(defaultValue = "0000") String hm,
            @RequestParam(defaultValue = "0") int localnum,
            @RequestParam(defaultValue = "false") boolean help
    ) {
        return ResponseEntity.ok(
            kmaService.fetchKmaData(
                apiKey, ymd, hm, localnum, help
            )
        );
    }

} // https://apihub.kma.go.kr/
