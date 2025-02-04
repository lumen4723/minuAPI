package com.lumen.minuAPI.Oauth;

import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequestMapping("/oauth")
public class OauthController {
    @GetMapping("")
    public ResponseEntity<?> Oauth() {
        List<Map<String, String>> oauthList = new ArrayList<>();

        Map<String, String> googleAuth = new HashMap<>();
        googleAuth.put("Google", "https://home.xminu.com:8888/oauth2/authorization/google");
        oauthList.add(googleAuth);
        
        // TODO: 다른 OAuth 추가
        // Map<String, String> facebookAuth = new HashMap<>();
        // facebookAuth.put("Facebook", "https://home.xminu.com:8888/oauth2/authorization/facebook");
        // oauthList.add(facebookAuth);

        Map<String, List<Map<String, String>>> result = new HashMap<>();
        result.put("result", oauthList);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/result")
    public ResponseEntity<?> OauthResult(
        @AuthenticationPrincipal OAuth2User oauth2User
    ) {
        return ResponseEntity.ok(oauth2User);
    }
}
