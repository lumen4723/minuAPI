package com.lumen.minuAPI.Index;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {

    @GetMapping("/")
    public String index(
        @RequestParam(required = false) String error
    ) {
        String msg = "This is Minu API";
        
        if (error != null && error.equals("404")) {
            msg += "<br>404 Not Found";
        }
        
        return msg;
    }

}
