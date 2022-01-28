package com.pitter.common.utils;

import com.pitter.domain.entity.member.Email;
import com.pitter.domain.entity.token.TokenType;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

@Slf4j
public class JwtUtils {

    private static final String jwtSecretKey = PropertiesUtils.getJwtSecretKey();
    private static final Long accessTokenPeriod = PropertiesUtils.getJwtAccessTokenPeriod();
    private static final Long refreshTokenPeriod = PropertiesUtils.getJwtRefreshTokenPeriod();

    public static String jwtTokenBuilder(Email email, TokenType tokenType, Date now) {
        Claims claims = Jwts.claims()
                .setSubject(email.getEmail());
        Long tokenPeriod = isAccessToken(tokenType) ? accessTokenPeriod : refreshTokenPeriod;
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + tokenPeriod))
                .signWith(SignatureAlgorithm.HS256, jwtSecretKey.getBytes())
                .compact();
    }

    public static boolean isValidToken(Email email, String token,Date date) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(jwtSecretKey)
                    .parseClaimsJws(token).getBody();

            return claims.getExpiration()
                        .after(date)
                    && claims.getSubject()
                        .equals(email.getEmail());
        } catch (SignatureException ex) {
            log.error("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty.");
        }
        return false;
    }

    private static boolean isAccessToken(TokenType tokenType) {
        return tokenType==TokenType.ACCESS_TOKEN;
    }
}
