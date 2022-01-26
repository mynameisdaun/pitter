package com.pitter.service;

import com.pitter.controller.dto.KakaoSignInResponse;
import com.pitter.controller.dto.KakaoUserInfoResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Service
public class KakaoTokenService {

    @Value("${spring.security.oauth2.client.registration.kakao.client-id}") private String clientId;
    @Value("${spring.security.oauth2.client.registration.kakao.redirect-uri}") private String redirectUri;

    private final String authDomain = "https://kauth.kakao.com";
    private final String accessTokenUri = "/oauth/token";
    private final String apiDomain = "https://kapi.kakao.com";
    private final String userInfoUri = "/v2/user/me";


    public KakaoSignInResponse getAccessToken(String authorization_code) {
        WebClient webClient = WebClient.builder()
                .baseUrl(authDomain)
                .build();
        return webClient.post()
                .uri(getAccessTokenRequestUrl(authorization_code))
                .retrieve()
                .bodyToMono(KakaoSignInResponse.class)
                .block();
    }

    public KakaoUserInfoResponse getKakaoUserInfo(String accessToken) {
        WebClient webClient = WebClient.builder()
                .baseUrl(apiDomain)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .defaultHeader("Authorization","Bearer "+accessToken)
                .build();
        Map<String, Object> result = webClient.post()
                .uri(apiDomain+userInfoUri)
                .retrieve()
                .bodyToMono(Map.class)
                .block();
        for(String x : result.keySet()) {
            System.out.println("key: "+x);
            System.out.println("value: "+result.get(x));
        }
        KakaoUserInfoResponse kakaoUserInfoResponse = new KakaoUserInfoResponse(result);
        System.out.println(kakaoUserInfoResponse);
        return kakaoUserInfoResponse;
    }

    private String getAccessTokenRequestUrl(String authorization_code) {
        StringBuilder sb = new StringBuilder(accessTokenUri);
        sb.append("?grant_type=")
                .append("authorization_code")
          .append("&client_id=")
                .append(clientId)
          .append("&redirect_uri=")
                .append(redirectUri)
          .append("&code=")
                .append(authorization_code);
        return sb.toString();
    }
}
