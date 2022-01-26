package com.pitter.config.auth;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter @NoArgsConstructor
public class JwtConfig {
    @Value("${com.pitter.jwtSecretKey}")
    public String jwtSecretKey;
    @Value("${com.pitter.jwtAccessTokenPeriod}")
    public Long accessTokenPeriod;
    @Value("${com.pitter.jwtRefreshTokenPeriod}")
    public Long refreshTokenPeriod;

}
