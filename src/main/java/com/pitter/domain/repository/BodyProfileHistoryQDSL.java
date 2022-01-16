package com.pitter.domain.repository;

import com.pitter.domain.entity.BodyProfileHistory;

import java.time.LocalDateTime;
import java.util.List;

public interface BodyProfileHistoryQDSL {

    List<BodyProfileHistory> findHistoryByCheckAtBetween(String nickName, LocalDateTime startDate, LocalDateTime endDate) throws Exception;
}
