package com.pitter.controller.dto;

import com.pitter.domain.entity.member.Email;
import com.pitter.domain.entity.member.Member;
import com.pitter.domain.entity.member.NickName;
import lombok.Getter;
import lombok.ToString;

import java.util.Map;

import static org.apache.commons.lang3.StringUtils.*;

@Getter @ToString
public class KakaoUserInfo {
    private NickName nickname;
    private String profileImageUrl;
    private Email email;

    public KakaoUserInfo(final Map<String, Object> userInfoResponse) {
        Map<String, Object> kakaoAccount = (Map<String, Object>) userInfoResponse.get("kakao_account");
        Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");
        this.email = new Email((String) kakaoAccount.get("email"));
        this.nickname = new NickName((String) profile.get("nickname"));
        this.profileImageUrl = (String) profile.get("profile_image_url");
    }

    public Member toMember() {
        if(email == null || nickname == null || isBlank(profileImageUrl)) {
            //TODO: 유저 정보가 잘 넘어오지 않았다는 Exception으로 변경해야 한다.
            throw new IllegalArgumentException("카카오로부터 유저 정보가 잘 넘어오지 않았습니다.");
        }
        return Member.createMember(nickname,email,"oath2UsPass!!");
    }
}
