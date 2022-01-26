package com.pitter.domain.entity.token;

import com.pitter.domain.entity.member.Member;
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
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Properties;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter @ToString
public class InternalToken {

    private String jwtSecretKey;
    private Long accessTokenPeriod;
    private Long refreshTokenPeriod;

    @Transient private String internalAccessToken;

    @Transient private LocalDateTime internalAccessTokenExpireAt;

    private String internalRefreshToken;

    private LocalDateTime internalRefreshTokenExpireAt;

    public InternalToken(Member member) {
        this.jwtSecretKey = PropertiesUtils.getJwtSecretKey();
        this.accessTokenPeriod = PropertiesUtils.getJwtAccessTokenPeriod();
        this.refreshTokenPeriod = PropertiesUtils.getJwtRefreshTokenPeriod();
        this.internalAccessToken = jwtTokenBuilder(member, accessTokenPeriod);
        this.internalRefreshToken = jwtTokenBuilder(member, refreshTokenPeriod);
        LocalDateTime now = LocalDateTime.now();
        this.internalAccessTokenExpireAt = now.plusSeconds(accessTokenPeriod/1000);
        this.internalRefreshTokenExpireAt = now.plusSeconds(refreshTokenPeriod/1000);
    }

    public boolean isValidAccessToken(Date date) {
        try {
            Jws<Claims> claims = Jwts.parser()
                    .setSigningKey(jwtSecretKey)
                    .parseClaimsJws(internalAccessToken);
            return claims.getBody()
                    .getExpiration()
                    .after(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean isValidRefreshToken(Date date) {
        try {
            Jws<Claims> claims = Jwts.parser()
                    .setSigningKey(jwtSecretKey)
                    .parseClaimsJws(internalRefreshToken);
            return claims.getBody()
                    .getExpiration()
                    .after(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public String jwtTokenBuilder(Member member, Long tokenPeriod) {
        Claims claims = Jwts.claims()
                .setSubject(member.getEmail().getEmail());
        claims.put("role", member.getRole().getRole());
        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + tokenPeriod))
                .signWith(SignatureAlgorithm.HS256, jwtSecretKey)
                .compact();
    }
}
