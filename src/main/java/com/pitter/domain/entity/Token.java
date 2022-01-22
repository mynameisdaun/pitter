package com.pitter.domain.entity;

import io.jsonwebtoken.*;
import lombok.*;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity @Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) @AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
public class Token extends BaseEntity {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "email")
    private Member member;

    private String accessToken;

    private LocalDateTime accessTokenExpiredAt;

    private String refreshToken;

    private LocalDateTime refreshTokenExpiredAt;

    private LocalDateTime issuedAt;

    @Value("${com.pitter.jwtSecretKey}") @Transient
    private String jwtSecretKey;

    @Value("${com.pitter.jwtAccessTokenPeriod}")
    Long accessTokenPeriod;

    @Value("${com.pitter.jwtRefreshTokenPeriod}")
    Long refreshTokenPeriod;

    private Token(Member member, String accessToken, LocalDateTime accessTokenExpiredAt, String refreshToken, LocalDateTime refreshTokenExpiredAt, LocalDateTime issuedAt) {

    }

    public Token generateToken(Member member) {
        String accessToken = tokenBuilder(member, accessTokenPeriod);
        String refreshToken = tokenBuilder(member, refreshTokenPeriod);
        LocalDateTime now = LocalDateTime.now();

        return new Token(member, accessToken, now.plusNanos(accessTokenPeriod),
                         refreshToken, now.plusNanos(refreshTokenPeriod), now);


    }

    private String tokenBuilder(Member member, Long tokenPeriod) {
        Claims claims = Jwts.claims().setSubject(member.getEmail());
        claims.put("role", member.getRole().getRole());
        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + tokenPeriod))
                .signWith(SignatureAlgorithm.HS256, jwtSecretKey)
                .compact();
    }

    public boolean verifyToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser()
                    .setSigningKey(secretKey)
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

}
