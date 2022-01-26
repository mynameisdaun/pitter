package com.pitter.domain.repository.bodyprofilehistory;

import com.pitter.domain.entity.bodyprofilehistory.BodyProfileHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BodyProfileHistoryRepository extends JpaRepository<BodyProfileHistory, Long>,
                                                      BodyProfileHistoryQDSL {

}
