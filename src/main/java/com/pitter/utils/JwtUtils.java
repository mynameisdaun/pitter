package com.pitter.utils;

import com.pitter.domain.entity.member.Member;
import com.pitter.domain.entity.token.SocialToken;
import com.pitter.domain.entity.token.TokenType;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class JwtUtils {

    private static final String jwtSecretKey = PropertiesUtils.getJwtSecretKey();
    private static final Long accessTokenPeriod = PropertiesUtils.getJwtAccessTokenPeriod();
    private static final Long refreshTokenPeriod = PropertiesUtils.getJwtRefreshTokenPeriod();

    public static String jwtTokenBuilder(Member member, TokenType tokenType) {
        Claims claims = Jwts.claims()
                .setSubject(member.getEmail().getEmail());
        claims.put("role", member.getRole().getRole());
        Date now = new Date();
        Long tokenPeriod = isAccessToken(tokenType) ? accessTokenPeriod : refreshTokenPeriod;

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + tokenPeriod))
                .signWith(SignatureAlgorithm.HS256, jwtSecretKey)
                .compact();
    }

    public static boolean isValidToken(String token, Date date) {
        try {
            Jws<Claims> claims = Jwts.parser()
                    .setSigningKey(jwtSecretKey)
                    .parseClaimsJws(token);
            return claims.getBody()
                    .getExpiration()
                    .after(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private static boolean isAccessToken(TokenType tokenType) {
        return tokenType==TokenType.ACCESS_TOKEN;
    }
}
