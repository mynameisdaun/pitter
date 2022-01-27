package com.pitter.controller.dto;

import com.pitter.domain.entity.member.Email;
import com.pitter.domain.entity.token.InternalApiRequestToken;
import com.pitter.domain.entity.token.TokenType;
import lombok.*;
import org.apache.commons.lang3.EnumUtils;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@ToString @Getter
public class TokenValidateRequest {
    @NotNull Email email;
    @NotNull String token;
    @NotNull TokenType tokenType;

    private void validateTokenType(TokenType tokenType) {
        if(EnumUtils.isValidEnumIgnoreCase(TokenType.class, tokenType.getKey())) {
            throw new IllegalArgumentException("토큰 타입은 반드시 accessToken 혹은 refreshToken 이어야 합니다.");
        }
    }

    public InternalApiRequestToken toTokenEntity() {
        return new InternalApiRequestToken(email, tokenType, token);
    }

    public void setEmail(String email) {
        this.email = new Email(email);
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setTokenType(String tokenType) {
        if(tokenType.equals(TokenType.ACCESS_TOKEN.getKey())){
            this.tokenType = TokenType.ACCESS_TOKEN;
        } else {
            this.tokenType = TokenType.REFRESH_TOKEN;
        }
        validateTokenType(this.tokenType);
    }
}

