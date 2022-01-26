package com.pitter.domain.repository.bodyprofilehistory;

import com.pitter.domain.entity.bodyprofilehistory.BodyProfileHistory;
import com.pitter.domain.entity.member.NickName;

import java.time.LocalDateTime;
import java.util.List;

public interface BodyProfileHistoryQDSL {

    List<BodyProfileHistory> findHistoryByCheckAtBetween(NickName nickName, LocalDateTime startDate, LocalDateTime endDate) throws Exception;
}
