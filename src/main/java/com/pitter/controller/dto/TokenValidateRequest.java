package com.pitter.controller.dto;

import com.pitter.domain.entity.member.Email;
import com.pitter.domain.entity.token.InternalApiRequestToken;
import com.pitter.domain.entity.token.TokenType;
import com.pitter.common.exception.TokenTypeException;
import lombok.*;

import javax.validation.constraints.NotBlank;

import static com.pitter.domain.entity.token.TokenType.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString @Getter @Setter
public class TokenValidateRequest {

    @NotBlank String email;
    @NotBlank String token;
    @NotBlank String tokenType;

    public void setEmail(String email) {
        this.email = email;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public InternalApiRequestToken toTokenEntity() {
        return new InternalApiRequestToken(new Email(email), validateTokenType(tokenType), token);
    }

    private TokenType validateTokenType(String tokenType) {
        if(tokenType.equals(ACCESS_TOKEN.getKey())) {
            return ACCESS_TOKEN;
        }
        if (tokenType.equals(REFRESH_TOKEN.getKey())) {
            return REFRESH_TOKEN;
        } else {
            throw new TokenTypeException("허용되지 않은 토큰 타입입니다.");
        }
    }
}
