package com.lumen.minuAPI.Youtube;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/youtube")
public class YoutubeController {

    @Autowired
    private YoutubeService youtubeService;

    @GetMapping
    public ResponseEntity<YoutubeDTO> searchYoutube(
        @RequestParam String title,
        @RequestParam(defaultValue = "10") int row
    ) {
        return ResponseEntity.ok(
            youtubeService.fetchYoutubeData(title, row)
        );
    }

}
