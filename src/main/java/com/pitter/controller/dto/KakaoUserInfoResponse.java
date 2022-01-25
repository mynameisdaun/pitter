package com.pitter.controller.dto;

import com.pitter.domain.entity.member.Email;
import lombok.Getter;
import lombok.ToString;

import java.util.Map;

@Getter @ToString
public class KakaoUserInfoResponse {
    private String nickname;
    private String profile_image_url;
    private Email email;

    public KakaoUserInfoResponse(final Map<String, Object> userInfoResponse) {
        Map<String, Object> kakaoAccount = (Map<String, Object>) userInfoResponse.get("kakao_account");
        Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");
        this.email = new Email((String) kakaoAccount.get("email"));
        this.nickname = (String) profile.get("nickname");
        this.profile_image_url = (String) profile.get("profile_image_url");
    }
}
