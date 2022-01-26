package com.pitter.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor @Getter
public class TokenValidateResponse {
    private String message;
    private String code;
}
