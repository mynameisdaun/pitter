package com.pitter.domain.entity.token;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@RequiredArgsConstructor
public enum TokenType {

    ACCESS_TOKEN("accessToken"),
    REFRESH_TOKEN("refreshToken");

    private final String key;
}
