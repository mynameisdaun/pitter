package com.pitter.domain.entity.token;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SocialType {

    KAKAO("KAKAO"),
    GOOGLE("GOOGLE"),
    NAVER("NAVER"),
    PITTER("PITTER");

    private final String key;
}
