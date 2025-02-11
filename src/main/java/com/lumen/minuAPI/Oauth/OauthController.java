package com.lumen.minuAPI.Oauth;

import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
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
        Map<String, String> result = new HashMap<>();

        result.put("Google", "https://api.xminu.com/oauth2/authorization/google");
        result.put("Github", "https://api.xminu.com/oauth2/authorization/github");
        result.put("Discord", "https://api.xminu.com/oauth2/authorization/discord");
        result.put("Naver", "https://api.xminu.com/oauth2/authorization/naver");
        result.put("Kakao", "https://api.xminu.com/oauth2/authorization/kakao");

        // 라인은 profile만 제대로 가져오고 나머지는 안됨
        result.put("Line", "https://api.xminu.com/oauth2/authorization/line");

        // facebook은 비지니스 인증을 받아야 로그인시 Warning이 뜨지않음
        // result.put("Facebook", "https://api.xminu.com/oauth2/authorization/facebook");

        // instagram는 Oauth2를 지원하지 않음
        // result.put("Instagram", "https://api.xminu.com/oauth2/authorization/instagram");

        // 애플 : 돈 내야댐 안함, X : Oauth2 너무 복잡함 안함, 마소 : 로그인이 안됨 안함
        // 토스 : 사업자 등록증 필요함 안함

        return ResponseEntity.ok(result);
    }

    @GetMapping("/result")
    public ResponseEntity<?> OauthResult(
        @AuthenticationPrincipal OAuth2User oauth2User
    ) {
        return ResponseEntity.ok(oauth2User);
    }
}
