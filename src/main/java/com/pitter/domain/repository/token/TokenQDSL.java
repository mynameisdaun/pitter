package com.pitter.domain.repository.token;

import com.pitter.domain.entity.member.Email;
import com.pitter.domain.entity.member.Member;
import com.pitter.domain.entity.token.Token;

import java.util.Optional;

public interface TokenQDSL {
    Optional<Token> findByEmail(Email email);
    Optional<Token> findByMember(Member member);
}
