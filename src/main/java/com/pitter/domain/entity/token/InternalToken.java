package com.pitter.domain.entity.token;

import com.pitter.domain.entity.member.Member;
import com.pitter.utils.JwtUtils;
import com.pitter.utils.PropertiesUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Embeddable;
import javax.persistence.Transient;
import java.time.LocalDateTime;
import java.util.Date;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter @ToString
public class InternalToken {

    @Transient private String internalAccessToken;
    @Transient private LocalDateTime internalAccessTokenExpireAt;
    private String internalRefreshToken;
    private LocalDateTime internalRefreshTokenExpireAt;

    public InternalToken(Member member) {
        Long accessTokenPeriod = PropertiesUtils.getJwtAccessTokenPeriod();
        Long refreshTokenPeriod = PropertiesUtils.getJwtRefreshTokenPeriod();
        this.internalAccessToken = JwtUtils.jwtTokenBuilder(member, TokenType.ACCESS_TOKEN);
        this.internalRefreshToken = JwtUtils.jwtTokenBuilder(member, TokenType.REFRESH_TOKEN);
        LocalDateTime now = LocalDateTime.now();
        this.internalAccessTokenExpireAt = now.plusSeconds(accessTokenPeriod/1000);
        this.internalRefreshTokenExpireAt = now.plusSeconds(refreshTokenPeriod/1000);
    }
}
