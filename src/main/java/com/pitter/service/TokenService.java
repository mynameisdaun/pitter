package com.pitter.service;

import com.pitter.controller.dto.TokenValidateRequest;
import com.pitter.domain.entity.member.Email;
import com.pitter.domain.entity.member.Member;
import com.pitter.domain.repository.token.TokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenService {
    
    private final TokenRepository tokenRepository;

    public void validateAccessToken(Email email, String token) {
    }

    public void validateRefreshToken(Email email, String token) {
    }
}
