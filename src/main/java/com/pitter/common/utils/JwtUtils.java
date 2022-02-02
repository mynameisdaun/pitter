package com.pitter.common.utils;

import com.pitter.config.auth.CustomUserDetailsService;
import com.pitter.domain.entity.member.Email;
import com.pitter.domain.entity.token.TokenType;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;

import static com.pitter.common.utils.DateUtils.now;

@Component @Slf4j
@RequiredArgsConstructor
public class JwtUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    @Value("${com.pitter.jwtSecretKey}")
    private String jwtSecretKey;

    @Value("${com.pitter.jwtAccessTokenPeriod}")
    private Long jwtAccessTokenPeriod;

    private final CustomUserDetailsService customUserDetailsService;

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

    public boolean validateToken(String token) {
            Jwts.parser()
                .setSigningKey(jwtSecretKey)
                .parseClaimsJws(token);
            return true;
    }

    public Authentication getAuthentication(String token) {
        String email = getEmailFromJwtToken(token);
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    private String getEmailFromJwtToken(String token) {
        return Jwts.parser()
                .setSigningKey(jwtSecretKey)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

}
