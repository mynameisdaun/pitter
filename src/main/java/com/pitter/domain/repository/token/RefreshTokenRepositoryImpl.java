package com.pitter.domain.repository.token;

import com.pitter.domain.entity.member.Email;
import com.pitter.domain.entity.member.Member;
import com.pitter.domain.entity.token.QRefreshToken;
import com.pitter.domain.entity.token.RefreshToken;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

import static com.pitter.domain.entity.token.QRefreshToken.*;

@RequiredArgsConstructor
public class RefreshTokenRepositoryImpl implements RefreshTokenQDSL {

    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<RefreshToken> findByEmail(Email email) {
        return Optional.ofNullable(queryFactory
            .select(refreshToken)
                .from(refreshToken)
                .where(refreshToken.member.email.eq(email))
                .fetchOne());
    }

    @Override
    public Optional<RefreshToken> findByMember(Member member) {
        return Optional.ofNullable(queryFactory
                .select(refreshToken)
                .from(refreshToken)
                .where(refreshToken.member.eq(member))
                .fetchOne());
    }

    @Override
    public Optional<RefreshToken> findByToken(String token) {
        return Optional.ofNullable(queryFactory
                .select(refreshToken)
                .from(refreshToken)
                .where(refreshToken.token.eq(token))
                .fetchOne());
    }
}
