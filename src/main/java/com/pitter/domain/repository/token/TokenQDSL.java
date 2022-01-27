package com.pitter.domain.repository.token;

import com.pitter.domain.entity.member.Email;
import com.pitter.domain.entity.member.Member;
import com.pitter.domain.entity.token.Token;

public interface TokenQDSL {
    Token findByEmail(Email email);
    Token findByMember(Member member);
}
