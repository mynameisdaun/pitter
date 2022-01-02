package com.pitter.domain.repository;

import com.pitter.domain.entity.BodyProfileHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BodyProfileHistoryRepository extends JpaRepository<BodyProfileHistory, Long>,
                                                      BodyProfileHistoryQDSL {

}
