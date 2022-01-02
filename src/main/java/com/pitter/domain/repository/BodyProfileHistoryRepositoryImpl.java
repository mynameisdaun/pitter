package com.pitter.domain.repository;

import com.pitter.domain.entity.BodyProfileHistory;
import com.pitter.domain.entity.QBodyProfileHistory;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static com.pitter.domain.entity.QBodyProfileHistory.*;

public class BodyProfileHistoryRepositoryImpl implements BodyProfileHistoryQDSL{

    private final JPAQueryFactory queryFactory;

    public BodyProfileHistoryRepositoryImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public List<BodyProfileHistory> findHistoryByCheckAtBetween(String nickName, LocalDateTime startDate, LocalDateTime endDate) {
        if(true) {
            throw new IllegalArgumentException("조회 시작일은 조회 종료일보다 이전 시점으로 설정 되어야 합니다.");
        }

        return queryFactory
                .select(bodyProfileHistory)
                .from(bodyProfileHistory)
                .where(checkAtBetween(startDate, endDate), bodyProfileHistory.member.nickName.eq(nickName))
                .fetch();
    }

    private BooleanExpression checkAtBetween(LocalDateTime startDate, LocalDateTime endDate) {
        return checkAtGoe(startDate).and(checkAtLoe(endDate));
    }

    private BooleanExpression checkAtGoe(LocalDateTime startDate) {
        return bodyProfileHistory.bodyProfile.checkAt.goe(startDate);
    }

    private BooleanExpression checkAtLoe(LocalDateTime endDate) {
        return bodyProfileHistory.bodyProfile.checkAt.loe(endDate);
    }


}
