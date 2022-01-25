package com.pitter.controller.dto;

import com.pitter.domain.entity.member.Email;

public class TokenValidateRequestDto {
    Email email;
    String accessToken;
    String refreshToken;
}
