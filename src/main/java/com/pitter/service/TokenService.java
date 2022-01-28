package com.pitter.service;

import com.pitter.controller.dto.TokenValidateResponse;
import com.pitter.domain.entity.token.InternalApiRequestToken;
import com.pitter.domain.entity.token.TokenType;
import com.pitter.domain.repository.token.TokenRepository;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.pitter.controller.dto.TokenCode.*;
import static com.pitter.common.utils.DateUtils.now;

@Service
@RequiredArgsConstructor
public class TokenService {
    
    private final TokenRepository tokenRepository;

    public TokenValidateResponse validate(InternalApiRequestToken internalApiRequestToken) {
        TokenType tokenType = internalApiRequestToken.getTokenType();

        if(isAccessToken(tokenType)) {
            return validateAccessToken(internalApiRequestToken);
        }
        return validateRefreshToken(internalApiRequestToken);
    }

    private TokenValidateResponse validateAccessToken(InternalApiRequestToken internalApiRequestToken) {
        try{
            internalApiRequestToken.isValid(now());
        }
        catch(ExpiredJwtException e) {
            return EXPIRED_ACCESS_TOKEN.toDto();
        }
        return SUCCESS.toDto();
    }

    private TokenValidateResponse validateRefreshToken(InternalApiRequestToken internalApiRequestToken) {
        try{
            internalApiRequestToken.isValid(now());
        }
        catch(ExpiredJwtException e) {
            return EXPIRED_REFRESH_TOKEN.toDto();
        }




        return SUCCESS.toDto();
    }

    private boolean isAccessToken(TokenType tokenType) {
        return tokenType == TokenType.ACCESS_TOKEN;
    }
}
