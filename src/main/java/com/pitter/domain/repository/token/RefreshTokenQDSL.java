package com.pitter.domain.repository.token;

import com.pitter.domain.entity.member.Email;
import com.pitter.domain.entity.member.Member;
import com.pitter.domain.entity.token.RefreshToken;
import com.pitter.domain.entity.token.Token;

import java.util.Optional;

public interface RefreshTokenQDSL {
    Optional<RefreshToken> findByEmail(Email email);
    Optional<RefreshToken> findByMember(Member member);
    Optional<RefreshToken> findByToken(String token);
}
