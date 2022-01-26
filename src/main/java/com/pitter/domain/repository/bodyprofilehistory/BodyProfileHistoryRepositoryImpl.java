package com.pitter.domain.repository.bodyprofilehistory;

import com.pitter.domain.entity.bodyprofilehistory.BodyProfileHistory;
import com.pitter.domain.entity.member.NickName;
import com.pitter.domain.repository.bodyprofilehistory.BodyProfileHistoryQDSL;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDateTime;
import java.util.List;

import static com.pitter.domain.entity.bodyprofilehistory.QBodyProfileHistory.bodyProfileHistory;


public class BodyProfileHistoryRepositoryImpl implements BodyProfileHistoryQDSL {

    private final JPAQueryFactory queryFactory;

    public BodyProfileHistoryRepositoryImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public List<BodyProfileHistory> findHistoryByCheckAtBetween(NickName nickName, LocalDateTime startDate, LocalDateTime endDate) {
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
