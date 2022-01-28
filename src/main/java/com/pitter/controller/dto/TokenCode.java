package com.pitter.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TokenCode {

    SUCCESS("PL000","로그인 성공"),
    INVALID_PARAMETER("PL001","요청 파라미터를 확인해 주세요."),
    EXPIRED_ACCESS_TOKEN("PL002", "만료된 엑세스 토큰입니다"),
    EXPIRED_REFRESH_TOKEN("PL003", "만료된 리프레쉬 토큰입니다"),
    INVALID_SIGNATURE("PL004", "유효하지 않은 토큰 서명입니다"),
    INVALID_TOKEN_TYPE("PL005", "tokenType은 accessToken 혹은 refreshToken 이어야 합니다."),
    INVALID_TOKEN_FORMAT("PL006", "지원하지 않는 토큰 형식입니다.");

    private final String code;
    private final String message;

    public TokenValidateResponse toDto() {
        return new TokenValidateResponse(this.code, this.message);
    }
}



