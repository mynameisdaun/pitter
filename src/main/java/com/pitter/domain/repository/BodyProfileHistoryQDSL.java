package com.pitter.domain.repository;

import com.pitter.domain.entity.BodyProfileHistory;
import com.pitter.domain.wrapper.NickName;

import java.time.LocalDateTime;
import java.util.List;

public interface BodyProfileHistoryQDSL {

    List<BodyProfileHistory> findHistoryByCheckAtBetween(NickName nickName, LocalDateTime startDate, LocalDateTime endDate) throws Exception;
}
