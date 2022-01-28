package com.pitter.service;

import com.pitter.controller.dto.TokenValidateResponse;
import com.pitter.domain.entity.token.InternalApiRequestToken;
import com.pitter.domain.entity.token.TokenType;
import com.pitter.domain.repository.token.TokenRepository;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
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


        try{
            internalApiRequestToken.isExpired(now());
        } catch(ExpiredJwtException e) {
            if(internalApiRequestToken.getTokenType()==TokenType.ACCESS_TOKEN) {
                return EXPIRED_ACCESS_TOKEN.toDto();
            }
            return EXPIRED_REFRESH_TOKEN.toDto();
        } catch(SignatureException e) {
            return INVALID_SIGNATURE.toDto();
        } catch(Exception e) {
            return INVALID_TOKEN_FORMAT.toDto();
        }

        if(tokenType==TokenType.ACCESS_TOKEN) {
            return validateAccessToken(internalApiRequestToken);
        }
        return validateRefreshToken(internalApiRequestToken);
    }

    private TokenValidateResponse validateAccessToken(InternalApiRequestToken internalApiRequestToken) {
        if(internalApiRequestToken.isExpired(now())){
            return EXPIRED_ACCESS_TOKEN.toDto();
        }
        return SUCCESS.toDto();
    }

    private TokenValidateResponse validateRefreshToken(InternalApiRequestToken internalApiRequestToken) {
        if(internalApiRequestToken.isExpired(now())){
            return EXPIRED_ACCESS_TOKEN.toDto();
        }
//        Token token = tokenRepository.findByEmail(internalApiRequestToken.getEmail())
//                .orElseThrow(InvalidRefreshTokenException::new);
//        return token.isValidRefreshToken(internalApiRequestToken).toDto();
        return null;
    }
}
