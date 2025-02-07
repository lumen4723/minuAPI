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
        googleAuth.put("Google", "https://api.xminu.com/oauth2/authorization/google");
        oauthList.add(googleAuth);
        
        // facebook은 비지니스 인증을 받아야 로그인시 Warning이 뜨지않음
        Map<String, String> facebookAuth = new HashMap<>();
        facebookAuth.put("Facebook", "https://api.xminu.com/oauth2/authorization/facebook");
        oauthList.add(facebookAuth);

        Map<String, String> githubAuth = new HashMap<>();
        githubAuth.put("Github", "https://api.xminu.com/oauth2/authorization/github");
        oauthList.add(githubAuth);

        Map<String, String> discordAuth = new HashMap<>();
        discordAuth.put("Discord", "https://api.xminu.com/oauth2/authorization/discord");
        oauthList.add(discordAuth);

        // 네이버

        // 카카오

        // 토스

        // 라인

        // 애플 : 돈 내야댐 안함, X : Oauth2 너무 복잡함 안함, 마소 : 로그인이 안됨 안함

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
