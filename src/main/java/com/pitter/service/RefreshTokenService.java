package com.pitter.service;

import com.pitter.common.exception.MemberNotFoundException;
import com.pitter.common.exception.TokenRefreshException;
import com.pitter.common.utils.JwtUtils;
import com.pitter.domain.entity.member.Email;
import com.pitter.domain.entity.member.Member;
import com.pitter.domain.entity.token.RefreshToken;
import com.pitter.domain.repository.member.MemberRepository;
import com.pitter.domain.repository.token.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Transient;
import java.util.Optional;

import static com.pitter.common.utils.DateUtils.now;

@Service
@RequiredArgsConstructor
@Transactional
public class RefreshTokenService {
    @Value("{com.pitter.refreshTokenPeriod}") @Transient
    private Long refreshTokenPeriod;
    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshToken findByToken(String token) {
        RefreshToken refreshToken = refreshTokenRepository.findByToken(token)
                .orElseThrow(TokenRefreshException::new);
        return refreshToken;
    }

    public RefreshToken createToken(Member member) {
        RefreshToken refreshToken = RefreshToken.createRefreshToken(member, refreshTokenPeriod);
        refreshTokenRepository.save(refreshToken);
        return RefreshToken.createRefreshToken(member, refreshTokenPeriod);
    }

    public RefreshToken verifyExpiration(RefreshToken token) {
        RefreshToken refreshToken = token.verifyExpiration();
        refreshTokenRepository.save(refreshToken);
        return refreshToken;
    }

    public void deleteByEmail(Email email) {
        RefreshToken token = refreshTokenRepository.findByEmail(email).orElseThrow(MemberNotFoundException::new);
        refreshTokenRepository.delete(token);
    }


}
