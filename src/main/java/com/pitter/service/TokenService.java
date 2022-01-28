package com.pitter.service;

import com.pitter.common.exception.UnIdentifiedRefreshTokenException;
import com.pitter.controller.dto.TokenValidateResponse;
import com.pitter.domain.entity.token.InternalApiRequestToken;
import com.pitter.domain.entity.token.Token;
import com.pitter.domain.entity.token.TokenType;
import com.pitter.domain.repository.token.TokenRepository;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.pitter.common.utils.DateUtils.now;
import static com.pitter.controller.dto.TokenCode.*;

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
        if(!matchedWithDB(internalApiRequestToken)){
            throw new UnIdentifiedRefreshTokenException("확인할 수 없는 리프레쉬 토큰입니다.");
        }
        return SUCCESS.toDto();
    }

    private boolean matchedWithDB(InternalApiRequestToken internalApiRequestToken) {
        Token token = tokenRepository.findByEmail(internalApiRequestToken.getEmail())
                .orElseThrow(UnIdentifiedRefreshTokenException::new);
        return token.getInternalApiRequestToken()
                .getToken()
                .equals(internalApiRequestToken.getToken())
            && token.getInternalApiRequestToken().getTokenExpireAt().getTime() == internalApiRequestToken.getTokenExpireAt().getTime();
    }
    private boolean isAccessToken(TokenType tokenType) {
        return tokenType == TokenType.ACCESS_TOKEN;
    }
}
