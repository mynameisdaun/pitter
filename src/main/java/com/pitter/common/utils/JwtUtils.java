package com.pitter.common.utils;

import com.pitter.domain.entity.member.Email;
import com.pitter.domain.entity.token.TokenType;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

import static com.pitter.common.utils.DateUtils.now;

@Component @Slf4j
public class JwtUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    @Value("${com.pitter.jwtSecretKey}")
    private String jwtSecretKey;

    @Value("${com.pitter.jwtAccessTokenPeriod}")
    private Long jwtAccessTokenPeriod;

    public String jwtTokenBuilder(Email email, Date now) {
        Claims claims = Jwts.claims()
                .setSubject(email.getEmail())
                .setIssuedAt(now);
        Long tokenPeriod = jwtAccessTokenPeriod;
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + tokenPeriod))
                .signWith(SignatureAlgorithm.HS256, jwtSecretKey.getBytes())
                .compact();
    }

    public String getEmailFromJwtToken(String token) {
        return Jwts.parser()
                .setSigningKey(jwtSecretKey)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validateJwtToken(String token) {
        try {
            Jwts.parser()
                .setSigningKey(jwtSecretKey)
                .parseClaimsJws(token);
            return true;
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
