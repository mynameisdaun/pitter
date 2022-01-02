package com.pitter.domain.repository;

import com.pitter.domain.entity.BodyProfileHistory;

import java.util.List;

public interface BodyProfileHistoryQDSL {

    List<BodyProfileHistory> findHistoryByPeriod();
}
