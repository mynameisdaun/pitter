package com.pitter.domain.entity;

import io.jsonwebtoken.*;
import lombok.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Entity @Getter @Component
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class Token extends BaseEntity implements Serializable {

    @Transient private final Logger logger = LoggerFactory.getLogger(Token.class);

    @Transient private static String jwtSecretKey;

    @Transient private static Long accessTokenPeriod;

    @Transient private static Long refreshTokenPeriod;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "email")
    private Member member;

    private String accessToken;

    private LocalDateTime accessTokenExpiredAt;

    private String refreshToken;

    private LocalDateTime refreshTokenExpiredAt;

    private LocalDateTime issuedAt;

    private Token(Member member, String accessToken, LocalDateTime accessTokenExpiredAt, String refreshToken, LocalDateTime refreshTokenExpiredAt, LocalDateTime issuedAt) {
        this.member = member;
        this.accessToken = accessToken;
        this.accessTokenExpiredAt = accessTokenExpiredAt;
        this.refreshToken = refreshToken;
        this.refreshTokenExpiredAt = refreshTokenExpiredAt;
        this.issuedAt = issuedAt;
    }

    public static Token generateToken(Member member) {
        String accessToken = tokenBuilder(member, accessTokenPeriod);
        String refreshToken = tokenBuilder(member, refreshTokenPeriod);
        LocalDateTime now = LocalDateTime.now();
        return new Token(member, accessToken, now.plusNanos(accessTokenPeriod), refreshToken, now.plusNanos(refreshTokenPeriod), now);
    }

    public boolean verifyToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser()
                    .setSigningKey(jwtSecretKey)
                    .parseClaimsJws(token);
            return claims.getBody()
                    .getExpiration()
                    .after(new Date());
        } catch (SignatureException e) {
            logger.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }
        return false;
    }

    private static String tokenBuilder(Member member, Long tokenPeriod) {
        Claims claims = Jwts.claims()
                .setSubject(member.getEmail());
        claims.put("role", member.getRole().getRole());
        Date now = new Date();
        System.out.println("hello~");
        System.out.println(tokenPeriod);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + tokenPeriod))
                .signWith(SignatureAlgorithm.HS256, jwtSecretKey)
                .compact();
    }

    @Value("${com.pitter.jwtSecretKey}")
    public void setJwtSecretKey(String jwtSecretKey) {
        this.jwtSecretKey = jwtSecretKey;
    }

    @Value("${com.pitter.jwtAccessTokenPeriod}")
    public void setAccessTokenPeriod(Long accessTokenPeriod) {
        this.accessTokenPeriod = accessTokenPeriod;
    }

    @Value("${com.pitter.jwtRefreshTokenPeriod}")
    public void setRefreshTokenPeriod(Long refreshTokenPeriod) {
        this.refreshTokenPeriod = refreshTokenPeriod;
    }
}
