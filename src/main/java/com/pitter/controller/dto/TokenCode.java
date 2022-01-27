package com.pitter.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TokenCode {

    SUCCESS("PL000","로그인 성공"),
    EXPIRED_ACCESS_TOKEN("PL001", "만료된 엑세스 토큰입니다"),
    EXPIRED_REFRESH_TOKEN("PL002", "만료된 리프레쉬 토큰입니다"),
    INVALID_SIGNATURE("PL003", "유효하지 않은 토큰 서명입니다");

    private final String code;
    private final String message;

    public TokenValidateResponse toDto() {
        return new TokenValidateResponse(this.code, this.message);
    }
}



