package com.pitter.domain.repository.token;

import com.pitter.domain.entity.token.RefreshToken;
import com.pitter.domain.entity.token.Token;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long>, RefreshTokenQDSL { }
