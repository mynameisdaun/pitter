package com.pitter.controller.dto;

import com.pitter.domain.entity.member.Email;
import com.pitter.domain.entity.token.TokenType;
import lombok.Data;
import org.apache.commons.lang3.EnumUtils;

import javax.validation.constraints.NotNull;

@Data
public class TokenValidateRequest {
    @NotNull Email email;
    @NotNull String token;
    @NotNull TokenType tokenType;

    public TokenValidateRequest(Email email, String token, TokenType tokenType) {
        validateTokenType(tokenType);
        this.email = email;
        this.token = token;
        this.tokenType = tokenType;
    }

    private void validateTokenType(TokenType tokenType) {
        if(EnumUtils.isValidEnumIgnoreCase(TokenType.class, tokenType.getKey())) {
            throw new IllegalArgumentException("토큰 타입은 반드시 accessToken 혹은 refreshToken 이어야 합니다.");
        }
    }
}

