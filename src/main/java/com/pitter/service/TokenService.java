package com.pitter.service;

import com.pitter.controller.dto.TokenValidateResponse;
import com.pitter.domain.entity.token.InternalApiRequestToken;
import com.pitter.domain.entity.token.Token;
import com.pitter.domain.entity.token.TokenType;
import com.pitter.domain.repository.token.TokenRepository;
import com.pitter.exception.InvalidRefreshTokenException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.pitter.controller.dto.TokenCode.*;
import static com.pitter.utils.DateUtils.*;

@Service
@RequiredArgsConstructor
public class TokenService {
    
    private final TokenRepository tokenRepository;

    public TokenValidateResponse validate(InternalApiRequestToken internalApiRequestToken) {
        TokenType tokenType = internalApiRequestToken.getTokenType();
        //토큰의 실 소유자와 API를 요청한 아이디가 다른 경우
        System.out.println("[========== we are debugging 1 ==========]");
        if(internalApiRequestToken.isValidSubject()) {
            return INVALID_SIGNATURE.toDto();
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
