package com.pitter.controller.dto;

import com.pitter.domain.wrapper.Email;

public class TokenValidateRequestDto {
    Email email;
    String accessToken;
    String refreshToken;
}
