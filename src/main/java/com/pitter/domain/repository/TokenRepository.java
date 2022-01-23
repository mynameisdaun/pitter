package com.pitter.domain.repository;

import com.pitter.domain.entity.BodyProfileHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<BodyProfileHistory, String> {


}
