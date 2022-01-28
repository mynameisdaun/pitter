package com.pitter.domain.repository.token;

import com.pitter.domain.entity.member.Email;
import com.pitter.domain.entity.member.Member;
import com.pitter.domain.entity.token.Token;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

import static com.pitter.domain.entity.token.QToken.token;

@RequiredArgsConstructor
public class TokenRepositoryImpl implements TokenQDSL {

    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<Token> findByEmail(Email email) {
        return Optional.ofNullable(queryFactory
                .select(token)
                .from(token)
                .where(token.member.email.eq(email))
                .fetchOne());
    }

    @Override
    public Optional<Token> findByMember(Member member) {
        return Optional.ofNullable(queryFactory
                .select(token)
                .from(token)
                .where(token.member.eq(member))
                .fetchOne());
    }

}
