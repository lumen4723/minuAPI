package com.lumen.minuAPI.OpenAPI.Binance;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/binance")
public class BinanceController {
    
    @Value("${api-key.binance.API_Key}")
    private String apiKey;

    @Value("${api-key.binance.Secret_Key}")
    private String secretKey;

    @Autowired
    private BinanceService binanceService;

    @GetMapping
    public ResponseEntity<BinanceDTO> getBinanceData(
        @RequestParam(defaultValue = "BTCUSDT") String symbol,
        @RequestParam(defaultValue = "15m") String interval,
        @RequestParam(defaultValue = "10") int limit
    ) {
        return ResponseEntity.ok(
            binanceService.fetchBinanceData(
                apiKey, secretKey, symbol, interval, limit
            )
        );
    }
}
