package com.pitter.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor @Getter
public class TokenValidateResponse {
    private String code;
    private String message;
    private String accessToken;
    private String refreshToken;

    public TokenValidateResponse(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
